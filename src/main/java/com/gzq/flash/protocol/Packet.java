package com.gzq.flash.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-09-27 14:50.
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 获取指令
     * @return
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand() ;

}
