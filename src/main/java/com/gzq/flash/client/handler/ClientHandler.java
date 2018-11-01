package com.gzq.flash.client.handler;

import com.gzq.flash.protocol.PacketCodeC;
import com.gzq.flash.protocol.Packet;
import com.gzq.flash.protocol.request.LoginRequestPacket;
import com.gzq.flash.protocol.response.LoginResponsePacket;
import com.gzq.flash.protocol.response.MessageResponsePacket;
import com.gzq.flash.util.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-27 18:19.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");
        // 创建登录对象
        for (int i = 0; i < 100; i++) {
            LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
            loginRequestPacket.setUserId(UUID.randomUUID().toString());
            loginRequestPacket.setUserName("gzq");
            loginRequestPacket.setPassword("123456");
            // 编码
            ByteBuf buf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), loginRequestPacket);
            // 写数据
            ctx.channel().writeAndFlush(buf);
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                SessionUtil.bindSession(ctx.channel());
                System.out.println(new Date() + ": 客户端登录成功");
            } else {
                System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
        }

    }
}
