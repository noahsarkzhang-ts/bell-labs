package org.noahsrak.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/19
 */
public class NacosSdkClient {


    public static void main(String[] args) throws NacosException, InterruptedException {
        String serverAddr = "192.168.7.115";
        String dataId = "com.hst.boss.dal";
        String group = "boss";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        /*new Director().execute(content);*/
        configService.addListener(dataId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("recieve:" + configInfo);

                /*new Director().execute(content);*/
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });

        /*boolean isPublishOk = configService.publishConfig(dataId, group, "content");
        System.out.println(isPublishOk);*/

        Thread.sleep(3000000);
        /*content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);*/

        /*boolean isRemoveOk = configService.removeConfig(dataId, group);
        System.out.println(isRemoveOk);
        Thread.sleep(3000);

        content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        Thread.sleep(300000);*/

    }
}
