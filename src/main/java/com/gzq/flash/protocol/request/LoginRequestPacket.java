package com.gzq.flash.protocol.request;

import com.gzq.flash.protocol.Packet;
import lombok.Data;

import static com.gzq.flash.protocol.command.Command.LOGIN_REQUEST;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-27 15:14.
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
