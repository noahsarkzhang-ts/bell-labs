package org.noahsark.server.main;

import Ice.InitializationData;
import Ice.Properties;
import Ice.PropertiesI;
import Ice.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author: noahsark
 * @version:
 * @date: 2020/12/8
 */
@Component
public class PrinterCommandLineRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrinterCommandLineRunner.class);

    @Autowired
    private PrinterClient client;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("PrinterClient is starting......");

        InitializationData localInitializationData = new InitializationData();
        localInitializationData.properties = Util.createProperties();

        String endpoint = "HstIceGrid/Locator:tcp -h 192.168.68.62 -p 4061";
        localInitializationData.properties.setProperty("Ice.Default.Locator", endpoint);

        client.run();
    }
}
