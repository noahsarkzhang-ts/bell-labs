package org.noahsrak.nacos.interceptor;

import org.noahsrak.nacos.context.ConfigEnv;
import org.noahsrak.nacos.context.Context;
import org.noahsrak.nacos.transformation.DynamiConfigItem;
import org.noahsrak.nacos.extraction.ConfigExtraction;
import org.noahsrak.nacos.replacement.ConfigReplacement;
import org.noahsrak.nacos.transformation.CommandExecutor;

import java.util.List;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/21
 */
public class PlaceholderInterceptor implements Interceptor {

    private ConfigEnv env = ConfigEnv.getInstance();

    private CommandExecutor executor = new CommandExecutor();

    @Override
    public String intercept(String content) {

        Context context = new Context(content);

        List<DynamiConfigItem> items = ConfigExtraction.extract(content);
        context.setItems(items);

        items.stream().forEach(item -> executor.resolve(env,item));
        System.out.println("items = " + items);

        String target = ConfigReplacement.replace(content,items);

        return target;
    }
}
