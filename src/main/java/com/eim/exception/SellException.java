package com.eim.exception;

import com.eim.enums.ExceptionEnum;
import lombok.Data;

/**
 * Created by Zy on 2018/12/11.
 */
@Data
public class SellException extends RuntimeException {

    private Integer code;

    private String message;

    public SellException(Integer code,String message){
        super(message);
        this.code = code;
    }

    public SellException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

}
