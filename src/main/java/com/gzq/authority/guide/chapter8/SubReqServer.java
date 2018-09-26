package com.gzq.authority.guide.chapter8;

import com.gzq.authority.guide.chapter8.protobuf.pojo.SubscribeReqProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-08-16 15:17.
 */
public class SubReqServer {

    public void bind(int port) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    //使用nio模式
                    .channel(NioServerSocketChannel.class)
                    //设置 半接受队列大小
                    .option(ChannelOption.SO_BACKLOG, 100)
                    //设置boss线程处理handler
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //设置worker线程处理handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 添加半包解码器
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            //添加解码目标类
                            ch.pipeline().addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));
                            //添加字段长度的分隔符解码器
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());

                            //设置编码器
                            ch.pipeline().addLast(new ProtobufEncoder());
                            //设置真正的处理逻辑
                            ch.pipeline().addLast(new SubReqServerHandler());
                        }

                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {

            }
        }
        new SubReqServer().bind(port);
    }
}
