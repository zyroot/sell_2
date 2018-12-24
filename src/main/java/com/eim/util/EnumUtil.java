package com.eim.util;

import com.eim.dataObject.ProductCategory;
import com.eim.enums.CodeEnum;
import com.eim.repository.ProductCategoryRepository;
import com.eim.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 枚举工具类
 *
 * Created by Zy on 2018/12/17.
 */
public class EnumUtil {


    /**
     * <T extends CodeEnum> 泛型说明
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeEnum> T getCode(Integer code, Class<T> enumClass){
        //遍历该枚举的内容
        for (T t : enumClass.getEnumConstants()) {
            //进行匹配
            if(t.getCode().equals(code)){
                //匹配返回该枚举
                return t;
            }
        }
        return null;
    }


}
