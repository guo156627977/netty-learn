package com.gzq.flash.protocol.response;

import com.gzq.flash.protocol.Packet;
import lombok.Data;

import static com.gzq.flash.protocol.command.Command.LOGIN_RESPONSE;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-27 18:38.
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
