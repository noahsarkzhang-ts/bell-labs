package org.noahsrak.nacos.transformation;

import org.noahsrak.nacos.context.ConfigEnv;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/21
 */
public class EnvCommandResolver implements CommandResolver {
    @Override
    public String resolve(ConfigEnv env, DynamiConfigItem command) {
        String result = env.get(command.getCommand());
        System.out.println("result = " + result);
        command.setTarget(result);

        return result;
    }
}
