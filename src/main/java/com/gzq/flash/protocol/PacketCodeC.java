package com.gzq.flash.protocol;

import com.gzq.flash.protocol.request.LoginRequestPacket;
import com.gzq.flash.protocol.request.MessageRequestPacket;
import com.gzq.flash.protocol.response.LoginResponsePacket;
import com.gzq.flash.protocol.response.MessageResponsePacket;
import com.gzq.flash.serialize.Serializer;
import com.gzq.flash.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.gzq.flash.protocol.command.Command.*;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-27 16:56.
 */
public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();
    private  final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private  final Map<Byte, Serializer> serializerMap;

    // private Serializer serializer;


    public PacketCodeC() {
        //fixme 这里每new一个对象就有一个这个，如果实际生产用 需要解决
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }



    public ByteBuf encode(ByteBuf byteBuf,Packet packet) {
        // 1. 创建 ByteBuf 对象
        // ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 2. 序列化 java 对象
        //todo 这里序列化后期可以抽象成模板方法复用
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);
        //跳过协议版本号
        byteBuf.skipBytes(1);

        //序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        //指令
        byte command = byteBuf.readByte();

        //数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];

        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);

        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }


    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }

}
