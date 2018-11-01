package com.gzq.flash.serialize;

import com.gzq.flash.serialize.impl.JSONSerializer;

/**
 * The interface Serializer.
 *
 * @author guozhiqiang
 * @description
 * @created 2018 -09-27 15:26.
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * Gets serialize alogrithm.
     * 序列化算法
     * @return the serialize alogrithm
     */
    byte getSerializerAlogrithm();


    /**
     * Serialize byte [ ].
     * java 对象转换成二进制
     *
     * @param object the object
     * @return the byte [ ]
     */
    byte[] serialize(Object object);


    /**
     * Deserialize t.
     * 二进制转换成 java 对象
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @param bytes the bytes
     * @return the t
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
