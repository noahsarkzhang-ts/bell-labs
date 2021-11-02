package org.noahsrak.nacos.listener;

import org.noahsrak.nacos.interceptor.Interceptor;
import org.noahsrak.nacos.interceptor.PlaceholderInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/22
 */
public class ConfigExecutor {

    private List<Interceptor> interceptors = new ArrayList<>();

    public ConfigExecutor() {
        init();
    }

    private void init() {
        interceptors.add(new PlaceholderInterceptor());
    }

    public void execute(String content, Consumer<String> action) {

        try {
            interceptors.stream().forEach(interceptor -> {
                String result = interceptor.intercept(content);
                System.out.println("result = " + result);

                action.accept(result);

            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
