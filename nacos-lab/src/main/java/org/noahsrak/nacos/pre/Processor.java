package org.noahsrak.nacos.pre;

import org.noahsrak.nacos.config.AgentConfig;
import org.noahsrak.nacos.context.ConfigEnv;
import org.noahsrak.nacos.nacos.NacosClient;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/22
 */
interface Processor {
    void process(NacosClient client, AgentConfig config, ConfigEnv env);
}
