package com.gzq.flash.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-26 17:12.
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端在"+new Date()+"时间执行了channelActive()方法");
        System.out.println(new Date() + ": 客户端写出数据");
        // 1. 获取数据
        ByteBuf byteBuf = getByteBuf(ctx);
        // 2. 写数据
        ctx.channel().writeAndFlush(byteBuf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 1. 获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        // 2. 准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = "你好，闪电侠！".getBytes(Charset.forName("UTF-8"));
        // 3. 填充数据到 ByteBuf
        buffer.writeBytes(bytes);
        return buffer;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + ": 客户端端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }
}
