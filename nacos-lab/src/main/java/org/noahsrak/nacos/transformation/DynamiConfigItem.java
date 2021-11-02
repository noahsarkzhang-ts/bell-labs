package org.noahsrak.nacos.transformation;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/19
 */
public class DynamiConfigItem {

    private String source;

    private String target;

    private String command;

    private ItemType type;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DynamiConfigItem{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", command='" + command + '\'' +
                ", type=" + type +
                '}';
    }
}


