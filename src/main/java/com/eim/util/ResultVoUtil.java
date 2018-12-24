package com.eim.util;

import com.eim.VO.ResultVO;

/**
 * 返回结果工具类
 * Created by Zy on 2018/12/11.
 */
public class ResultVoUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }
    //什么都不传
    public static ResultVO success(){
        return success(null);
    }
    //error
    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        resultVO.setData(null);
        return resultVO;
    }
}
