package org.noahsrak.nacos.transformation;

import org.noahsrak.nacos.context.ConfigEnv;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/21
 */
public class CommandExecutor implements CommandResolver {

    private Map<ItemType, CommandResolver> maps = new HashMap<>();

    public CommandExecutor() {
        maps.put(ItemType.ENV, new EnvCommandResolver());
        maps.put(ItemType.URL, new UrlCommandResolver());
        maps.put(ItemType.SHELL, new ShellCommandResovler());
    }

    @Override
    public String resolve(ConfigEnv env, DynamiConfigItem command) {

        return maps.get(command.getType()).resolve(env,command);
    }
}
