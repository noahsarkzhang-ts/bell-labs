package org.noahsark.ssl;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

/**
 * SSL 能力检测
 *
 * @author zhangxt
 * @date 2023/02/16 16:51
 **/
public class SslChecker {

    public static void main(String[] args) {

        try {
            System.out.println("Suported SSL Protocols : " + String.join(" ",
                    SSLContext.getDefault().getSupportedSSLParameters().getProtocols()));


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
