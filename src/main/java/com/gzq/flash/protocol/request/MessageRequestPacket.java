package com.gzq.flash.protocol.request;

import com.gzq.flash.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.gzq.flash.protocol.command.Command.MESSAGE_REQUEST;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-28 9:50.
 */
@Data
@AllArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toUserId;

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
