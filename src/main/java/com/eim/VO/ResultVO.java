package com.eim.VO;

import lombok.Data;

/**
 * http 返回最外层对象
 * Created by Zy on 2018/12/10.
 */
@Data
public class ResultVO<T> {
    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体信息. */
    private T data;

}
