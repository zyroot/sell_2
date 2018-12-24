package com.eim.util;

import java.util.Random;

/**
 * 主键生成工具类
 * 格式 时间 + 随机数
 * Created by Zy on 2018/12/12.
 */
public class KeyUtil {

    public static synchronized String uniKey(){
        long l = System.currentTimeMillis();
        Random random = new Random();
        int random6 = random.nextInt(900000) + 100000;
        return String.valueOf(l)+String.valueOf(random6);
    }

}
