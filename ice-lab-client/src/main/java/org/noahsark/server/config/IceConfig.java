package org.noahsark.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: noahsark
 * @version:
 * @date: 2020/12/8
 */
@Component
@ConfigurationProperties("ice")
public class IceConfig {

    private Adapter adapter;

    public static class Adapter {
        private String endpoints;

        public String getEndpoints() {
            return endpoints;
        }

        public void setEndpoints(String endpoints) {
            this.endpoints = endpoints;
        }
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }
}
