package com.gzq.authority.guide.chapter8;

import com.gzq.authority.guide.chapter8.protobuf.pojo.SubscribeReqProto;
import com.gzq.authority.guide.chapter8.protobuf.pojo.SubscribeRespProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-08-16 16:00.
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            // ctx.writeAndFlush(subReq(i));
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("guozhiqiang");
        builder.setProductName("Netty Book");
        ArrayList<String> address = new ArrayList<>();
        address.add("HeNan JiYuan");
        address.add("BeiJing ChangPing");
        address.add("BeiJing ChaoYang");
        builder.addAllAddress(address);
        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeRespProto.SubscribeResp resp = (SubscribeRespProto.SubscribeResp) msg;
        System.out.println("收到服务器响应:[" + resp.toString() + "]");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
