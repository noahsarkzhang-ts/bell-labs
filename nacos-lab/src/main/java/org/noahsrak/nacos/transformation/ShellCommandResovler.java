package org.noahsrak.nacos.transformation;

import org.noahsrak.nacos.context.ConfigEnv;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/21
 */
public class ShellCommandResovler implements CommandResolver {
    @Override
    public String resolve(ConfigEnv env, DynamiConfigItem command) {

        // TODO
        // 1、执行命令
        // 2. 解析结果

        String result = "test";
        command.setTarget(result);

        return result;
    }
}
