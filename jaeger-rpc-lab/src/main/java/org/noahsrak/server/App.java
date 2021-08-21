package org.noahsrak.server;

import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.util.GlobalTracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @Bean
    public JaegerTracer getJaegerTracer() {
        return JaegerTracerHelper.initTracer("LoginServer");
    }

    public io.opentracing.Tracer tracer() {
        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration("LoginServer");
        io.jaegertracing.Configuration.SenderConfiguration sender = new io.jaegertracing.Configuration.SenderConfiguration();
        /**
         *  从https://tracing-analysis.console.aliyun.com/ 获取jaegerd的网关（Endpoint）
         *  第一次运行时，请设置当前用户的对应的网关
         */
        sender.withEndpoint("http://tracing-analysis-dc-sh.aliyuncs.com/adapt_i59bc0hdq2@c6992b0e181d539_i59bc0hdq2@53df7ad2afe8301/api/traces");
        config.withSampler(new io.jaegertracing.Configuration.SamplerConfiguration().withType("const").withParam(1));

        config.withReporter(new io.jaegertracing.Configuration.ReporterConfiguration().withSender(sender).withMaxQueueSize(10000));

        io.opentracing.Tracer tracer = config.getTracer();
        GlobalTracer.registerIfAbsent(tracer);
        return GlobalTracer.get();
    }
}
