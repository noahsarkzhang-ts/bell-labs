package org.noahsrak.client;

import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.metrics.Metrics;
import io.jaegertracing.internal.metrics.NoopMetricsFactory;
import io.jaegertracing.internal.reporters.CompositeReporter;
import io.jaegertracing.internal.reporters.LoggingReporter;
import io.jaegertracing.internal.reporters.RemoteReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.thrift.internal.senders.HttpSender;

public class JaegerTracerHelper {
    public static JaegerTracer initTracer(String service) {

        // aliyun
        //final String endPoint = "http://tracing-analysis-dc-sh.aliyuncs.com/adapt_i59bc0hdq2@c6992b0e181d539_i59bc0hdq2@53df7ad2afe8301/api/traces";

        // jaeger
        final String endPoint = "http://192.168.7.115:14268/api/traces";

        final CompositeReporter compositeReporter = new CompositeReporter(
                new RemoteReporter.Builder()
                        .withSender(new HttpSender.Builder(endPoint).build())
                        .build(),
                new LoggingReporter()
        );



        final Metrics metrics = new Metrics(new NoopMetricsFactory());

        JaegerTracer.Builder builder = new JaegerTracer.Builder(service)
                .withReporter(compositeReporter)
                .withMetrics(metrics)
                .withExpandExceptionLogs()
                .withSampler(new ConstSampler(true));

        return builder.build();
    }
}
