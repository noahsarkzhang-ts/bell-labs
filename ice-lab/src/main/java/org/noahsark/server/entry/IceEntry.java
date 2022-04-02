package org.noahsark.server.entry;

import Ice.Current;
import org.noahsark.server.rpc._PrinterDisp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ICE 服务
 *
 * @author zhangxt
 * @date 2022/03/31 15:34
 **/
@Component
public class IceEntry extends _PrinterDisp {

    private static final Logger LOGGER = LoggerFactory.getLogger(IceEntry.class);

    @Override
    public void printString(String s, Current __current) {
        LOGGER.info("receive a request: {}", s);
    }
}
