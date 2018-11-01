package com.gzq.flash.protocol.response;

import com.gzq.flash.protocol.Packet;
import lombok.Data;

import static com.gzq.flash.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-28 9:52.
 */
@Data
public class MessageResponsePacket extends Packet {
    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
