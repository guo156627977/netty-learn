package com.gzq.flash.server.handler;

import com.gzq.flash.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-10-19 15:33.
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            //没有给登录，直接关闭连接，生产环境不能这么做
            ctx.channel().close();
        } else {
            //取消校验
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
