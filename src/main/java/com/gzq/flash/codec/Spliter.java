package com.gzq.flash.codec;

import com.gzq.flash.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author guozhiqiang
 * @description  自定义的长度解码器，判断前面四位不为定义的魔术则直接关闭连接
 * @created 2018-09-30 11:09.
 */
public class Spliter extends LengthFieldBasedFrameDecoder {

    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        //调用父类构造器
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        if (in.getInt(in.readerIndex() ) != PacketCodeC.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
