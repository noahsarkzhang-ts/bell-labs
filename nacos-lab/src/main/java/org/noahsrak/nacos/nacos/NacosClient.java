package org.noahsrak.nacos.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.noahsrak.nacos.config.AgentConfig;

import java.util.Properties;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/22
 */
public class NacosClient {

    private String serverAddr;

    private String namespace = "dev";

    private ConfigService configService;

    public NacosClient() {
    }

    public NacosClient(AgentConfig agentConfig) {
        try {
            this.serverAddr = agentConfig.getServerAddr();

            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
            properties.put(PropertyKeyConst.NAMESPACE,agentConfig.getNamespace());
            configService = NacosFactory.createConfigService(properties);
        } catch (NacosException ex) {
            ex.printStackTrace();
        }
    }

    public NacosClient(Properties properties) {
        try {
            this.serverAddr = properties.getProperty(PropertyKeyConst.SERVER_ADDR);
            configService = NacosFactory.createConfigService(properties);
        } catch (NacosException ex) {
            ex.printStackTrace();
        }
    }

    public NacosClient(String serverAddr) {
        this.serverAddr = serverAddr;

        try {
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
            properties.put(PropertyKeyConst.NAMESPACE,namespace);
            configService = NacosFactory.createConfigService(properties);
        } catch (NacosException ex) {
            ex.printStackTrace();
        }
    }

    public String getConfig(String dataId, String group, long timeoutMs) throws NacosException {
        return configService.getConfig(dataId, group, timeoutMs);
    }

    public void addListener(String dataId, String group, Listener listener) throws NacosException {
        configService.addListener(dataId, group, listener);
    }

}
