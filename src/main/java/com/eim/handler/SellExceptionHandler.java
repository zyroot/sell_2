package com.eim.handler;

import com.eim.VO.ResultVO;
import com.eim.exception.SellException;
import com.eim.util.ResultVoUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常处理类
 * Created by Zy on 2018/12/20.
 */
@ControllerAdvice
public class SellExceptionHandler {

    /**
     * 发生异常统一处理
     * 此方法返回的http状态是200
     * @param e 捕获的异常
     * @return 自定义的返回信息
     */
//    @ExceptionHandler(value = SellException.class)
//    @ResponseBody
//    public ResultVO handlerSellerException(SellException e){
//        return ResultVoUtil.error(e.getCode(),e.getMessage());
//    }

    /**
     * 发生异常统一处理
     * 此方法返回的http状态是自定义状态（不再是200）
     * @param e 捕获的异常
     * @return 自定义的返回信息
     */
    @ExceptionHandler(value = SellException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handlerSellerException(SellException e){
    }

}
