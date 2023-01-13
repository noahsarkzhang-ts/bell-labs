package org.noahsark.docker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello Controller
 *
 * @author zhangxt
 * @date 2023/01/12 17:12
 **/
@RestController
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello,Docker";
    }
}
