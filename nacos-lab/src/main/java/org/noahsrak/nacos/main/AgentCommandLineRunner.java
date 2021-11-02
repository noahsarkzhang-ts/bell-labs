package org.noahsrak.nacos.main;

import org.noahsrak.nacos.Director;
import org.noahsrak.nacos.config.AgentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/10/22
 */
@Component
public class AgentCommandLineRunner implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(AgentCommandLineRunner.class);

    @Autowired
    private AgentConfig config;

    @Override
    public void run(String... args) throws Exception {
        Director director = new Director();

        director.process(config);

        log.info("Agent is started...");
    }
}
