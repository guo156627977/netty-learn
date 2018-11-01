package com.gzq.flash.client;

import com.gzq.flash.client.handler.LoginResponseHandler;
import com.gzq.flash.client.handler.MessageResponseHandler;
import com.gzq.flash.codec.PacketDecoder;
import com.gzq.flash.codec.PacketEncoder;
import com.gzq.flash.protocol.request.LoginRequestPacket;
import com.gzq.flash.protocol.request.MessageRequestPacket;
import com.gzq.flash.server.handler.LifeCyCleTestHandler;
import com.gzq.flash.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-07-11 10:38.
 */
public class NettyClient {

    //最大重试次数
    public static final int MAX_RETRY = 5;
    public static final int port = 8000;
    public static final String host = "127.0.0.1";

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap// 1.指定线程模型
                .group(group)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        // ch.pipeline().addLast(new StringEncoder());
                        // ch.pipeline().addLast(new FirstClientHandler());
                        // ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));

                        // ch.pipeline().addLast(new Spliter());
                        // ch.pipeline().addLast(new ClientHandler());

                        ch.pipeline().addLast(new LifeCyCleTestHandler());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, host,port,MAX_RETRY);
        //连接后发送数据
        // Channel channel = bootstrap.connect(host, port)
        //         .addListener(future -> {
        //             if (future.isSuccess()) {
        //                 System.out.println("连接成功！");
        //             } else {
        //                 System.err.println("连接失败！");
        //             }
        //         })
        //         .channel();
        // while (true) {
        //     channel.writeAndFlush(new Date() + ": Hello World!");
        //     Thread.sleep(2000);
        // }

    }

    /**
     * 指数退避重连，重试五次，失败后放弃重连
     * @param bootstrap
     * @param host
     * @param port
     * @param retry
     */
    public static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功！");
                Channel channel = ((ChannelFuture) future).channel();
                // 连接成功之后，启动控制台线程
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
                bootstrap.config().group().shutdownGracefully();
            } else {
                //重连了几次
                int order = (MAX_RETRY - retry) + 1;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                //本次重连间隔,指数退避的方式，比如每隔 1 秒、2 秒、4 秒、8 秒
                int delay = 1 << order;
                bootstrap.config().group().schedule(() ->
                    connect(bootstrap, host, port, retry - 1),delay,TimeUnit.SECONDS);
            }
        });
    }


    private static void startConsoleThread(Channel channel) {
        Scanner scanner = new Scanner(System.in);
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (SessionUtil.hasLogin(channel)) {
                    System.out.println("输入用户名登录:");
                    String userName = scanner.nextLine();
                    loginRequestPacket.setUserName(userName);

                    // 密码使用默认的
                    loginRequestPacket.setPassword("pwd");

                    // 发送登录数据包
                    channel.writeAndFlush(loginRequestPacket);
                    waitForLoginResponse();
                }else {
                    String toUserId = scanner.next();
                    String message = scanner.next();
                    channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
    /**
     * 无限重连
     *
     * @param bootstrap
     * @param host
     * @param port
     */
    public static void connect(Bootstrap bootstrap, String host, int port) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功！");
            } else {
                System.out.println("连接失败！");
                //递归调用自己
                connect(bootstrap, host, port);
            }
        });
    }

}
