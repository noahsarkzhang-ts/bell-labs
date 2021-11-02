package org.noahsrak.nacos.transformation;

import org.noahsrak.nacos.context.ConfigEnv;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/21
 */
public class UrlCommandResolver implements CommandResolver {
    @Override
    public String resolve(ConfigEnv env, DynamiConfigItem command) {

        // TOTO
        // 1. 解析参数
        // 2. 发起 http 请求
        // 3. 解析响应
        // 4. 返回结果

        String result = "test";
        command.setTarget(result);

        return result;
    }
}
