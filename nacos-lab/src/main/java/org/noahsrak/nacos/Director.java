package org.noahsrak.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import org.noahsrak.nacos.config.AgentConfig;
import org.noahsrak.nacos.context.ConfigEnv;
import org.noahsrak.nacos.listener.ConfigListener;
import org.noahsrak.nacos.nacos.NacosClient;
import org.noahsrak.nacos.pre.PubliceIpProcessor;
import org.noahsrak.nacos.store.FileStore;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/21
 */
public class Director {

    private ConfigEnv env = ConfigEnv.getInstance();

    private NacosClient client;

    private FileStore fileStore;

    public ConfigListener listener;

    private PubliceIpProcessor processor = new PubliceIpProcessor();

    public Director() {
    }

    public void process(AgentConfig agentConfig) {

        try {
            client = new NacosClient(agentConfig);
            fileStore = new FileStore(agentConfig);
            listener = new ConfigListener();

            // 1、初始化环境变量
            env.init();

            // 2、执行前置操作，如加载 pubicIp 配置
            // TODO
            processor.process(client,agentConfig,env);

            //3 、首次加载
            listener.firstLoad(agentConfig,client,fileStore::store);

            // 4、初始化配置文件信息，包括注册监听器监听配置变化
            // TODO
            listener.addListeners(agentConfig,client,fileStore::store);

        } catch (NacosException ex) {
            ex.printStackTrace();
        }

    }

}
