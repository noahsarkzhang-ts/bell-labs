package org.noahsrak.controller;

import com.codahale.metrics.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: noahsark
 * @version:
 * @date: 2021/5/25
 */

@Controller
@RequestMapping("/metric")
public class MainController {

//    @Autowired
//    private Meter requestMeter;
//
//    @Autowired
//    private Histogram responseSizes;
//
//    @Autowired
//    private Counter pendingJobs;

    @Autowired
    private Timer responses;

    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld() {

//        requestMeter.mark();
//
//        pendingJobs.inc();
//
//        responseSizes.update(new Random().nextInt(10));

        final Timer.Context context = responses.time();
        try {
            return "Hello World";
        } finally {
            context.stop();
        }
    }
}