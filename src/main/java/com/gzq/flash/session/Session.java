package com.gzq.flash.session;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The type Session.
 *
 * @author guozhiqiang
 * @description
 * @created 2018 -11-01 17:20.
 */
@Data
// @NoArgsConstructor
@AllArgsConstructor
public class Session {


    // 用户唯一性标识
    private String userId;

    private String userName;

    //todo 头像url，年龄，性别 ,etc
    // public Session(String userId, String userName) {
    //     this.userId = userId;
    //     this.userName = userName;
    // }
}
