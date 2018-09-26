package com.gzq.authority.guide.chapter8.protobuf.test;

import com.google.protobuf.InvalidProtocolBufferException;
import com.gzq.authority.guide.chapter8.protobuf.pojo.SubscribeReqProto;

import java.util.ArrayList;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-08-16 11:09.
 */
public class TestSubscribeReqProto {

    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("guozhiqiang");
        builder.setProductName("netty");
        ArrayList<String> address = new ArrayList<>();
        address.add("HeNan JiYuan");
        address.add("BeiJing ChangPing");
        address.add("BeiJing ChaoYang");
        builder.addAllAddress(address);
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("req = " + req.toString());
        SubscribeReqProto.SubscribeReq decode = decode(encode(req));
        System.out.println("decode = " + decode);
        byte[] encode = encode(decode);
        System.out.println(req.equals(decode));

    }

}
