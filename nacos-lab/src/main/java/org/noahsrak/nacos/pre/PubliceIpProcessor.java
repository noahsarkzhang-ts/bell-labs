package org.noahsrak.nacos.pre;

import com.alibaba.nacos.api.exception.NacosException;
import org.noahsrak.nacos.config.AgentConfig;
import org.noahsrak.nacos.context.ConfigEnv;
import org.noahsrak.nacos.nacos.NacosClient;
import org.yaml.snakeyaml.Yaml;

import java.lang.ref.SoftReference;
import java.util.Map;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/22
 */
public class PubliceIpProcessor implements Processor {

    @Override
    public void process(NacosClient client, AgentConfig config, ConfigEnv env) {

        try {
            String content = client.getConfig(config.getPre().getDataId(),config.getPre().getGroup(),5000);
            Yaml yaml = new Yaml();
            Map<String,String> publicIpMap =  yaml.load(content);

            // 根据 localIp 映射 publicIp
            String localIp = env.get("localip");
            System.out.println("localIp = " + localIp);

            String publicIp = publicIpMap.get(localIp);
            System.out.println("publicIp = " + publicIp);

            // 设置 publicIp 到 env中
            env.put("publicIp", publicIp);

        } catch (NacosException ex) {
            ex.printStackTrace();
        }

    }
}
