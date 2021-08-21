package org.noahsrak.server.controller;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: noahsark
 * @version:
 * @date: 2020/8/22
 */
@Controller
public class ServerAddressController {

    @Autowired
    Tracer tracer;

    @PostMapping("/getServerAddress")
    @ResponseBody
    public String getServerAddress(@RequestBody String name,
                                   HttpServletRequest request) {

        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headers.put(header, request.getHeader(header));
        }

        Tracer.SpanBuilder builder = null;
        SpanContext parentSpanContext = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapAdapter(headers));

        System.out.println(headers);

        if (null == parentSpanContext) {
            builder = tracer.buildSpan("getServerAddress");
        } else {
            builder = tracer.buildSpan("getServerAddress").asChildOf(parentSpanContext);
        }

        Span span = builder.start();
        span.setTag("name", name);

        System.out.println("getServerAddress " + name);

        span.log("hst getServerAddress service :" + name);
        span.finish();

        return "login " + name;
    }
}
