package org.noahsark.server.main;

import Ice.InitializationData;
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
    private PrinterServer server;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("PrinterServer is starting......");

        InitializationData localInitializationData = new InitializationData();
        localInitializationData.properties = Util.createProperties();
        /*localInitializationData.properties.setProperty("Ice.Default.Locator", "HstIceGrid/Locator:tcp -h 192.168.68.62 -p 4061");*/
        localInitializationData.properties.setProperty("PrinterAdapter.AdapterId", "PrinterAdapter");
        localInitializationData.properties.setProperty("PrinterAdapter.Endpoints", "tcp");
        /*localInitializationData.properties.setProperty("Ice.Trace.Network","2");
        localInitializationData.properties.setProperty("Ice.Trace.Protocol","1");
        localInitializationData.properties.setProperty("Ice.Trace.Locator","2");
        localInitializationData.properties.setProperty("Ice.StdErr","E:\\lab\\bell-labs\\ice-lab\\target\\logs\\ice-error.log");*/
        /*localInitializationData.properties.setProperty("Ice.StdOut","E:\\lab\\bell-labs\\ice-lab\\target\\logs\\ice-out.log");*/
        /*localInitializationData.properties.setProperty("Ice.LogFile","E:\\lab\\bell-labs\\ice-lab\\target\\logs\\ice-log.log");*/

        server.main("PrinterServer", args, localInitializationData);
    }
}
