package org.noahsrak.nacos.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/21
 */
public class ConfigEnv {

    private Map<String, String> env = new HashMap<>();

    private static class ConfigEnvHolder {
        private static final ConfigEnv INSTANCE = new ConfigEnv();
    }

    private ConfigEnv() {
        //init();
    }

    public static ConfigEnv getInstance() {
        return ConfigEnvHolder.INSTANCE;
    }

    public void init() {
        env.put("hostname","allen-pc");
        env.put("eth0","192.168.68.25");
        env.put("localip","192.168.68.25");
    }

    public void put(String key, String value) {
        env.put(key, value);
    }

    public String get(String key) {
        return env.get(key);
    }
}
