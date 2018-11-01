package com.gzq.flash.server.handler;

import com.gzq.flash.protocol.request.MessageRequestPacket;
import com.gzq.flash.protocol.response.MessageResponsePacket;
import com.gzq.flash.session.Session;
import com.gzq.flash.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-28 19:13.
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        //1. 拿到消息发送方的会话信息
        Session sesssion = SessionUtil.getSesssion(ctx.channel());

        //2. 通过消息发送方的会话消息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(sesssion.getUserId());
        messageResponsePacket.setFromUserName(sesssion.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        //3. 拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getCHannel(messageRequestPacket.getToUserId());

        //4, 将消息发送给接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        }else {
            //todo  这里可以放到mq/数据库中，等用户上线后给推送过去
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }
    }
}
