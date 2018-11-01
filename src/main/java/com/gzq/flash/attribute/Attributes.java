package com.gzq.flash.attribute;

import com.gzq.flash.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-28 10:04.
 */
public interface Attributes {

    // AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
