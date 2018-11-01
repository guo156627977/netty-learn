package com.gzq.flash.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.gzq.flash.serialize.Serializer;
import com.gzq.flash.serialize.SerializerAlogrithm;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-27 15:34.
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
