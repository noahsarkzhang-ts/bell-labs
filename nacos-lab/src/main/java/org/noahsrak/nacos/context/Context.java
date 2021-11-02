package org.noahsrak.nacos.context;

import org.noahsrak.nacos.transformation.DynamiConfigItem;

import java.util.List;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/21
 */
public class Context {

    /**
     *  原始配置文件
     */
    private String config;

    /**
     *  动态占位符
     */
    private List<DynamiConfigItem> items;

    /**
     *  最终的配置文件
     */
    private String target;

    public Context() {

    }

    public Context(String config) {
        this.config = config;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public List<DynamiConfigItem> getItems() {
        return items;
    }

    public void setItems(List<DynamiConfigItem> items) {
        this.items = items;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
