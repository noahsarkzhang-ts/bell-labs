package org.noahsark.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Tcp Server
 *
 * @author zhangxt
 * @date 2022/11/22 10:59
 **/
public class TcpServer {

    public static void main(String[] args) {

        // Netty的服务器端启动器，装配Netty组件
        new ServerBootstrap()
                // NioEventLoopGroup底层就是线程池+selector
                .group(new NioEventLoopGroup())
                // 通道
                .channel(NioServerSocketChannel.class)
                //“每一个”SocketChannel客户端连接上服务器端“都会”执行这个初始化器ChannelInitializer
                //但是每一个SocketChannel只能够让这个初始化器执行一次
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                System.out.println("initChannel start......");
                                //往处理器流水线pipeline添加处理器
                                //因为'客户端'发送数据会进行'字符串的编码'再发送到服务器端，所以这里要'创建一个字符串解码器'StringDecoder
                                nioSocketChannel.pipeline().addLast(new StringDecoder());
                                //添加接收数据需要的处理器适配器
                                nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    //重写通道的‘’读‘’方法,msg就是接收到的数据
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println(msg.toString()); //打印数据
                                        super.channelRead(ctx, msg);
                                    }
                                });
                                System.out.println("initChannel end......");
                            }
                        })
                .bind(8082);
    }

}
