package com.gzq.authority.guide.chapter8;

import com.gzq.authority.guide.chapter8.protobuf.pojo.SubscribeReqProto;
import com.gzq.authority.guide.chapter8.protobuf.pojo.SubscribeRespProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-08-16 15:28.
 */
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        if ("guozhiqiang".equalsIgnoreCase(req.getUserName())) {
            System.out.println("服务器接到订单请求:[" + req.toString() + "]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }

    }

    private SubscribeRespProto.SubscribeResp resp(int subReqID) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        //todo
        builder.setRespCode(0);
        // builder.setRespCode(1);
        builder.setDesc("success");
        //fixme 中文会乱码
        // builder.setDesc("订购成功，三天后发货，发送到订购地址");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
