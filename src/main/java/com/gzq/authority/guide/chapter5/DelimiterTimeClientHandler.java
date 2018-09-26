package com.gzq.authority.guide.chapter5;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-08-06 10:02.
 */
public class DelimiterTimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(DelimiterTimeClientHandler.class.getName());

    private int counter;

    static final String request = "Hi,郭志强.Welcome to Netty at BeiJing.$_ ";

    public DelimiterTimeClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(request.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;

        System.out.println(" Thi is  " + ++counter + " times receive server : [" + body + "]");

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.warning("Unexpected exception from downstream : " + cause.getMessage());
        ctx.close();
    }

}
