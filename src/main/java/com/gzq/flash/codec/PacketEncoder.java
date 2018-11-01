package com.gzq.flash.codec;

import com.gzq.flash.protocol.Packet;
import com.gzq.flash.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-28 18:54.
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        PacketCodeC.INSTANCE.encode(out, packet);
    }
}
