package com.gzq.flash.util;

import com.gzq.flash.attribute.Attributes;
import com.gzq.flash.session.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-28 10:20.
 */
public class SessionUtil {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();


    /**
     * 绑定session，登录时操作
     * @param session
     * @param channel
     */
    public static void bindSession(Session session, Channel channel) {
        //将userId 和 对应的TCP连接 channel 放到mao中
        userIdChannelMap.put(session.getUserId(), channel);
        //channel上打上登录属性session
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 取消绑定Session，登出时操作
     * @param channel
     */
    public static void unBindSession(Channel channel) {
        //判断是否登录
        if (hasLogin(channel)) {
            //移除map中的对应关系
            userIdChannelMap.remove(getSesssion(channel).getUserId());
            //channel上的session属性去掉，
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 判断有没有登录，根据channel上有没有session属性
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        // Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        // return loginAttr.get() != null;
       return channel.hasAttr(Attributes.SESSION);

    }

    /**
     * 获取channel中的session信息
     * @param channel
     * @return
     */
    public static Session getSesssion(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * 获取userId对应的channel
     * @param userId
     * @return
     */
    public static Channel getCHannel(String userId) {
        return userIdChannelMap.get(userId);
    }
}
