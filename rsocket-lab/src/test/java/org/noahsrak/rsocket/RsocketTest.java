package org.noahsrak.rsocket;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/11/1
 */
public class RsocketTest {

    @Test
    public void whenSendingAString_thenRevceiveTheSameString() {
        ReqResClient client = new ReqResClient();
        String string = "Hello RSocket";

        assertEquals(string, client.callBlocking(string));

        client.dispose();
    }
}
