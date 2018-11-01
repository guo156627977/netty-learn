package com.gzq.flash.protocol.command;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-27 14:55.
 */
public interface Command {

    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;

}
