package com.gzq.flash.server;

import com.gzq.flash.codec.PacketDecoder;
import com.gzq.flash.codec.PacketEncoder;
import com.gzq.flash.server.handler.AuthHandler;
import com.gzq.flash.server.handler.LifeCyCleTestHandler;
import com.gzq.flash.server.handler.LoginRequestHandler;
import com.gzq.flash.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-07-11 10:28.
 */
public class NettyServer {

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worder = new NioEventLoopGroup();
        serverBootstrap
                //设置boss线程和worker线程
                .group(boss, worder)
                //设置io模型为nio
                .channel(NioServerSocketChannel.class)
                //对应的是tcp/ip协议listen函数中的backlog参数，函数listen(int socketfd,int backlog)用来初始化服务端可连接队列
                .option(ChannelOption.SO_BACKLOG, 1024)
                //表示是否开启TCP底层心跳机制，true为开启
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //表示是否开始Nagle算法，true表示关闭，false表示开启，通俗地说，如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // ch.pipeline().addLast(new StringDecoder());
                        // ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                        //     @Override
                        //     protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                        //         System.out.println("msg = " + msg);
                        //     }
                        // });
                        // ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));

                        // ch.pipeline().addLast(new Spliter());
                        // ch.pipeline().addLast(new FirstServerHandler());

                        // inBound，处理读数据的逻辑链
                        // ch.pipeline().addLast(new InBoundHandlerA());
                        // ch.pipeline().addLast(new InBoundHandlerB());
                        // ch.pipeline().addLast(new InBoundHandlerC());
                        // // outBound，处理写数据的逻辑链
                        // ch.pipeline().addLast(new ServerHandler());
                        // ch.pipeline().addLast(new OutBoundHandlerA());
                        // ch.pipeline().addLast(new OutBoundHandlerB());
                        // ch.pipeline().addLast(new OutBoundHandlerC());

                        ch.pipeline().addLast(new LifeCyCleTestHandler());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());


                    }
                });
        bind(serverBootstrap, 8000);

    }

    public static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
