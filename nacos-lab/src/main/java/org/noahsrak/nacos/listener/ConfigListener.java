package org.noahsrak.nacos.listener;

import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.noahsrak.nacos.config.AgentConfig;
import org.noahsrak.nacos.nacos.NacosClient;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/22
 */
public class ConfigListener {

    private ConfigExecutor executor = new ConfigExecutor();

    public ConfigListener(){
    }

    public void addListeners(AgentConfig config, NacosClient client, Consumer<String> action) throws NacosException {

        client.addListener(config.getConfig().getDataId(),config.getConfig().getGroup(), new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("recieve config:\n" + configInfo);

                executor.execute(configInfo,action);
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });
    }

    public void firstLoad(AgentConfig config,NacosClient client,Consumer<String> action) throws NacosException {

        String content = client.getConfig(config.getConfig().getDataId(),config.getConfig().getGroup(),5000);
        executor.execute(content,action);
    }


}
