package org.noahsrak.server.controller;

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

        System.out.println("getServerAddress " + name);

        return "login " + name;
    }
}
