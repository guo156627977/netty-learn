package com.gzq.mytest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-10-11 15:01.
 */
public class DateTest {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        System.out.println("当前时间：" + sdf.format(now));

        Date afterDate = new Date(now.getTime() - 300000);
        System.out.println(sdf.format(afterDate));

    }
}
