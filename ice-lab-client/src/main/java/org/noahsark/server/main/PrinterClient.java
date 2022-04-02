package org.noahsark.server.main;

import Ice.Communicator;
import Ice.InitializationData;
import Ice.Util;
import org.noahsark.server.rpc.PrinterPrx;
import org.noahsark.server.rpc.PrinterPrxHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Printer client
 *
 * @author zhangxt
 * @date 2022/03/31 16:15
 **/
@Component
public class PrinterClient  {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrinterClient.class);

    private Communicator ic = null;

    public int run() {

        //获取Printer的远程代理，这里使用的stringToProxy方式
        String endpoint = "HstIceGrid/Locator:tcp -h 192.168.68.62 -p 4061";

        InitializationData localInitializationData = new InitializationData();
        localInitializationData.properties = Util.createProperties();
        localInitializationData.properties.setProperty("Ice.Default.Locator", endpoint);
        localInitializationData.properties.setProperty("Ice.Trace.Network","2");
        localInitializationData.properties.setProperty("Ice.Trace.Protocol","1");
        localInitializationData.properties.setProperty("Ice.Trace.Locator","2");
        localInitializationData.properties.setProperty("Ice.StdErr","E:\\lab\\bell-labs\\ice-lab-client\\ice-error.log");
        localInitializationData.properties.setProperty("Ice.StdOut","E:\\lab\\bell-labs\\ice-lab-client\\ice-out.log");
        localInitializationData.properties.setProperty("Ice.LogFile","E:\\lab\\bell-labs\\ice-lab-client\\ice-log.log");
        ic = Util.initialize(localInitializationData);

        // 直连模式：servcie:服务器 ip 端口
        /*Ice.ObjectPrx base = ic.stringToProxy("printerService:tcp -h 192.168.68.62 -p 30000");*/

        // 注册中心非 Node 模式 service@Adapter
        /*Ice.ObjectPrx base = ic.stringToProxy("printerService@PrinterAdapter");*/

        // // 注册中心 Node 模式 service
        Ice.ObjectPrx base = ic.stringToProxy("printerService");

        //通过checkedCast向下转换，获取Printer接口的远程，并同时检测根据传入的名称获取的服务单元是否Printer的代理接口，
        // 如果不是则返回null对象
        PrinterPrx printer = PrinterPrxHelper.checkedCast(base);
        if (printer == null) {
            LOGGER.info("printer is null");
        }
        //把Hello World传给服务端，让服务端打印出来，因为这个方法最终会在服务端上执行
        String parameter;
        for (int i = 0; i < 10; i++) {

            parameter = "xxx" + i;
            LOGGER.info("send request:{}",parameter);

            printer.printString("xxx" + i);

        }

        return 0;
    }

}
