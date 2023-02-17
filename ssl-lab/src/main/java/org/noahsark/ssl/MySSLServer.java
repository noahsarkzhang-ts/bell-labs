package org.noahsark.ssl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

/**
 * SSL Server
 *
 * @author zhangxt
 * @date 2023/02/15 15:08
 **/

public class MySSLServer implements Runnable, HandshakeCompletedListener {
    public static final int SERVER_PORT = 11123;
    private final Socket socket;
    private String peerCerName;
    public MySSLServer(Socket s) {
        socket = s;
    }
    public static void main(String[] args) throws Exception {

        System.setProperty("javax.net.debug", "ssl,handshake");

        String serverKeyStoreFile = "E:\\lab\\bell-labs\\ssl-lab\\src\\main\\resources\\cert\\server_ks";
        String serverKeyStorePwd = "123456";
        String myServerKeyPwd = "123456";
        String serverTrustKeyStoreFile = "E:\\lab\\bell-labs\\ssl-lab\\src\\main\\resources\\cert\\server_ks";
        String serverTrustKeyStorePwd = "123456";
        KeyStore serverKeyStore = KeyStore.getInstance("JKS");
        serverKeyStore.load(new FileInputStream(serverKeyStoreFile), serverKeyStorePwd.toCharArray());
        KeyStore serverTrustKeyStore = KeyStore.getInstance("JKS");
        serverTrustKeyStore.load(new FileInputStream(serverTrustKeyStoreFile), serverTrustKeyStorePwd.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(serverKeyStore, myServerKeyPwd.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(serverTrustKeyStore);
        SSLContext sslContext = SSLContext.getInstance("TLSv1");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
        SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(SERVER_PORT);
        sslServerSocket.setNeedClientAuth(true);
        while (true) {
            SSLSocket s = (SSLSocket)sslServerSocket.accept();
            MySSLServer cs = new MySSLServer(s);
            s.addHandshakeCompletedListener(cs);
            new Thread(cs).start();
        }
    }
    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Welcome~, enter exit to leave.");
            String s;
            while ((s = reader.readLine()) != null && !s.trim().equalsIgnoreCase("exit")) {
                writer.println("Echo: " + s);
            }
            writer.println("Bye~, " + peerCerName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void handshakeCompleted(HandshakeCompletedEvent event) {
        try {
            X509Certificate cert = (X509Certificate) event.getPeerCertificates()[0];
            peerCerName = cert.getSubjectX500Principal().getName();
        } catch (SSLPeerUnverifiedException ex) {
            ex.printStackTrace();
        }
    }
}
