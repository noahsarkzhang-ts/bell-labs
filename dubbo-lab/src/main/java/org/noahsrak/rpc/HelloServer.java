package org.noahsrak.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器
 *
 * @author zhangxt
 * @date 2021/11/08 13:40
 **/
public class HelloServer {

    public static void main(String[] args) {

        int port = 10240;
        HelloService service = new HelloServiceImpl();

        System.out.println("server startup...");
        try {
            ServerSocket server = new ServerSocket(port);
            for (; ; ) {
                final Socket socket = server.accept();
                new Thread(() -> {
                    try {
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                        String methodName = input.readUTF();
                        Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                        Object[] arguments = (Object[]) input.readObject();

                        System.out.println("\nreceive request:");
                        System.out.println("method:" + methodName);

                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        Method method = service.getClass().getMethod(methodName,parameterTypes);

                        Object result = method.invoke(service, arguments);
                        System.out.println("result = " + result);

                        output.writeObject(result);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
