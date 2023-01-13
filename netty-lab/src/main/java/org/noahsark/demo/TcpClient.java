package org.noahsark.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Tcp Client
 *
 * @author zhangxt
 * @date 2022/11/22 11:02
 **/
public class TcpClient {

    public static void main(String[] args) throws InterruptedException {

        //创建Netty客户端的启动器，装配Netty组件
        new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                //一旦执行这个应用立刻初始化，这个和childHandler有所不同
                //childHandler是需要socket连接上在初始化，这个不需要。。。。。
                .handler(
                        new ChannelInitializer<Channel>() {
                            @Override
                            protected void initChannel(Channel channel) throws Exception {
                                System.out.println("initChannel start......");
                                //由于发送的数据需要进行编码再发送，所以需要一个字符串编码器
                                //往通道流水线添加一个字符串编码器
                                channel.pipeline().addLast(new StringEncoder());
                                System.out.println("initChannel end......");
                            }
                        })
                // connect方法是“”异步“”的
                .connect("localhost", 8082)
                //坑点：由于connect方法是异步的，所以要同步。。。。。
                //由于connect方法是异步的，如果没有进行同步，可能会造成发送数据在连接服务器之前。
                //一般来说connect连接服务器大概需要>1s，而writeAndFlush是立刻发送数据，所以这里一定要使用sync方法进行同步
                .sync()
                // 获取通道。然后发送数据
                .channel()
                .writeAndFlush("hello你好");
    }
}
