package org.noahsark.server.main;

import Ice.ObjectImpl;
import org.noahsark.server.config.IceConfig;
import org.noahsark.server.entry.IceEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author: noahsark
 * @version:
 * @date: 2020/12/8
 */
@Component
public class PrinterServer extends Ice.Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrinterCommandLineRunner.class);

    @Autowired
    private IceConfig config;

    @Autowired
    private IceEntry iceEntry;

    @Override
    public int run(String[] args) {

        if (args.length > 0) {
            LOGGER.error(appName() + ": too many arguments");
            return 1;
        }

        ObjectImpl iceEntryImpl = iceEntry;

        String endpoints = "tcp";
        Ice.ObjectAdapter adapter = communicator().createObjectAdapterWithEndpoints("PrinterAdapter", endpoints);

        adapter.add(iceEntryImpl, communicator().stringToIdentity("printerService"));

        adapter.activate();
        communicator().waitForShutdown();
        return 0;

    }

    private String getEndpoints() {
        String endpoints;
        try {
            endpoints = config.getAdapter().getEndpoints();

            if (endpoints != null || endpoints.contains("localhost")) {
                endpoints = buildEndpointsByIp();
            }
        } catch (Exception ex) {
            LOGGER.error("Get an exception when read ice.adapter.endpoints ", ex);
            endpoints = buildEndpointsByIp();
        }

        return endpoints;
    }

    private String buildEndpointsByIp() {
        return "tcp -h " + getHostAddress() + " -p 33001";
    }

    private String getHostAddress() {
        String ip = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
            LOGGER.info("host address: {}", ip);
        } catch (Exception ex) {
            LOGGER.error("Catch an exception when get localhost", ex);
        }
        return ip;
    }

    public IceConfig getConfig() {
        return config;
    }

    public void setConfig(IceConfig config) {
        this.config = config;
    }
}
