package org.noahsrak.nacos.transformation;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/19
 */
public enum ItemType {
    ENV("ENV"),
    URL("URL"),
    SHELL("SHELL");

    private String value;

    ItemType(String value) {
        this.value = value;
    }

}
