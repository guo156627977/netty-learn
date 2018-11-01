package com.gzq.flash.server.handler.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-28 18:05.
 */
public class InBoundHandlerA extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerA: " + msg);
        ctx.channel().writeAndFlush(msg);
        // super.channelRead(ctx, msg);
    }
}
