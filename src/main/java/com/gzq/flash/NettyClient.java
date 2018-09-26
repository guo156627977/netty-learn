package com.gzq.flash;

import com.gzq.flash.handler.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
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
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        // ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new FirstClientHandler());
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
