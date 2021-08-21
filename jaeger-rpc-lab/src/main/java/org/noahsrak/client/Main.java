package org.noahsrak.client;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.tag.Tags;
import okhttp3.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Tracer tracer = JaegerTracerHelper.initTracer("PC-CLIENT");

        String url = "http://localhost:8888/login";
        OkHttpClient client = new OkHttpClient();
        Request.Builder request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), "allen"));


        Span span = tracer.buildSpan("okHttpMainCall").start();
        Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_CLIENT);
        Tags.HTTP_METHOD.set(span, "POST");
        Tags.HTTP_URL.set(span, url);

        span.setTag("userId",10001);
        span.setTag("businessId", 100);
        span.setTag("version", "3.30");


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
