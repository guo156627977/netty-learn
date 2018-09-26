package com.gzq.authority.guide.chapter5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-08-03 18:29.
 */
public class DelimiterTimeServerHandler extends ChannelInboundHandlerAdapter {

    private int counter=0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("This is "+ ++counter+" times receive client  : ["+body+"]");
        body += "$_";
        ByteBuf message = Unpooled.copiedBuffer(body.getBytes());

        ctx.write(message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
