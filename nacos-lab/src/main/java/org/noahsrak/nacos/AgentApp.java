package org.noahsrak.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/22
 */
@SpringBootApplication
public class AgentApp {

    public static void main(String[] args) {
        SpringApplication.run(AgentApp.class, args);

        try {
            TimeUnit.HOURS.sleep(24);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
