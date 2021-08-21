package org.noahsrak.client;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.tag.Tags;
import io.opentracing.util.GlobalTracer;
import okhttp3.*;

import java.io.IOException;

/**
 * @author: noahsark
 * @version:
 * @date: 2020/8/22
 */
public class PcClient {

    static {
        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration("PC-CLIENT");
        io.jaegertracing.Configuration.SenderConfiguration sender = new io.jaegertracing.Configuration.SenderConfiguration();
        /**
         *  从https://tracing-analysis.console.aliyun.com/ 获取jaegerd的网关（Endpoint）
         *  运行前，请设置当前用户的对应的网关
         */
        sender.withEndpoint("http://tracing-analysis-dc-sh.aliyuncs.com/adapt_i59bc0hdq2@c6992b0e181d539_i59bc0hdq2@53df7ad2afe8301/api/traces");
        config.withSampler(new io.jaegertracing.Configuration.SamplerConfiguration().withType("const").withParam(1));
        config.withReporter(new io.jaegertracing.Configuration.ReporterConfiguration().withSender(sender).withMaxQueueSize(10000));
        GlobalTracer.registerIfAbsent(config.getTracer());
    }

    public static void main(String[] args) {
//        invokeGetServerAddress();

        invokeLogin();
    }

    public static void invokeGetServerAddress() {

        //Tracer tracer = JaegerTracerHelper.initTracer("");
        Tracer tracer = GlobalTracer.get();

        String url = "http://192.168.7.115:8888/getServerAddress";
//        String url = "http://localhost:8888/login";
        OkHttpClient client = new OkHttpClient();
        Request.Builder request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), "FSP"));


        Span span = tracer.buildSpan("getServerAddressCall").start();
        Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_CLIENT);
        Tags.HTTP_METHOD.set(span, "POST");
        Tags.HTTP_URL.set(span, url);

        span.setTag("userId", 10002);
        span.setTag("businessId", 100);
        span.setTag("version", "3.31");

        tracer.activateSpan(span);

        tracer.inject(span.context(), Format.Builtin.HTTP_HEADERS, new RequestBuilderCarrier(request));

        client.newCall(request.build()).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });

        span.finish();

    }

    public static void invokeLogin() {
        /*Tracer tracer = GlobalTracer.get();*/

        Tracer tracer = JaegerTracerHelper.initTracer("rpc-client");

        /*tring url = "http://192.168.7.115:8888/login";*/
        String url = "http://localhost:8888/login";
        OkHttpClient client = new OkHttpClient();
        Request.Builder request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), "allen"));


        Span span = tracer.buildSpan("loginCall").start();
        Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_CLIENT);
        Tags.HTTP_METHOD.set(span, "POST");
        Tags.HTTP_URL.set(span, url);

        span.setTag("businessId", 100);
        span.setTag("userId", 10002);
        span.setTag("version", "3.31");

        tracer.activateSpan(span);

        tracer.inject(span.context(), Format.Builtin.HTTP_HEADERS, new RequestBuilderCarrier(request));
        client.newCall(request.build()).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });

        span.finish();

    }

}
