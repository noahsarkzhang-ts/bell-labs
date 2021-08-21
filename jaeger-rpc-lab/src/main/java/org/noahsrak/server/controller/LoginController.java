package org.noahsrak.server.controller;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapAdapter;
import io.opentracing.tag.Tags;
import org.noahsrak.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    Tracer tracer;

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody String name,
                        HttpServletRequest request) {

        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            headers.put(header, request.getHeader(header));
        }

        System.out.println(headers);

        Tracer.SpanBuilder builder = null;
        SpanContext parentSpanContext = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapAdapter(headers));
        if (null == parentSpanContext) {
            builder = tracer.buildSpan("login");
        } else {
            builder = tracer.buildSpan("login").asChildOf(parentSpanContext);
        }

        Span span = builder.start();
        tracer.activateSpan(span);
        span.setTag("name", name);
        Tags.SPAN_KIND.set(span, Tags.SPAN_KIND_CLIENT);

        System.out.println("login " + name);
        loginService.checkUser(name);

        span.log("hst user login service :" + name);
        span.finish();

        return "login " + name;
    }


}
