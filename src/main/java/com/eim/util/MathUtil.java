package com.eim.util;

import java.math.BigDecimal;

/**
 * 金额比较
 * Created by Zy on 2018/12/17.
 */
public class MathUtil {

    /**
     * 比较俩个金额是否一致
     */
    public static boolean isMath(double d1, double d2){

        double abs = Math.abs(d1 - d2);

        if(abs < 0.01){
            return true;
        }else{
            return false;
        }
    }
}
