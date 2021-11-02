package org.noahsrak.nacos.transformation;

import org.noahsrak.nacos.context.ConfigEnv;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/21
 */
interface CommandResolver {

    String resolve(ConfigEnv env, DynamiConfigItem command);

}
