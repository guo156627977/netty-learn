package com.gzq.flash.protocol.command;

import com.gzq.flash.protocol.Packet;
import com.gzq.flash.protocol.PacketCodeC;
import com.gzq.flash.protocol.request.LoginRequestPacket;
import com.gzq.flash.serialize.Serializer;
import com.gzq.flash.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-27 17:40.
 */
public class PacketCodeCTest {

    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId("123");
        loginRequestPacket.setUserName("zhangsan");
        loginRequestPacket.setPassword("password");
        //
        // PacketCodeC packetCodeC = new PacketCodeC();
        // ByteBuf byteBuf = packetCodeC.encode(ByteBufAllocator.DEFAULT,loginRequestPacket);
        // Packet decodedPacket = packetCodeC.decode(byteBuf);
        PacketCodeC packetCodeC = PacketCodeC.INSTANCE;
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        packetCodeC.encode(byteBuf, loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }
}
