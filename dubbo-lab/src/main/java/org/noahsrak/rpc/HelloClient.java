package org.noahsrak.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 客户端
 * @author zhangxt
 * @date 2021/11/08 13:42
 **/
public class HelloClient {

    public static void main(String[] args) {
        HelloService helloService = getProxy(HelloService.class,"127.0.0.1",10240);
        String result = helloService.sayHello("hi, charles");

        System.out.println("result = " + result);
    }

    public static  <T> T getProxy(Class<T> interfaceClass, String host, int port){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[] {interfaceClass},
                new InvocationHandler() {

                    public Object invoke(Object proxy, Method method, Object[]
                            arguments) throws Throwable {
                        Socket socket = new Socket(host, port);
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

                        output.writeUTF(method.getName());
                        output.writeObject(method.getParameterTypes());
                        output.writeObject(arguments);

                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                        return input.readObject();}
                });
    }
}
