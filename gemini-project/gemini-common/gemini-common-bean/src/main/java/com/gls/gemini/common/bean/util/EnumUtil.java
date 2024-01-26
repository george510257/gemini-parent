package com.gls.gemini.common.bean.util;

import com.gls.gemini.common.bean.interfaces.IEnums;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnumUtil {

    /**
     * 根据key获取枚举
     *
     * @param key   枚举key
     * @param clazz 枚举类
     * @return 返回枚举
     */
    public <E extends Enum<E> & IEnums> E getEnumByCode(Integer key, Class<E> clazz) {
        for (E e : clazz.getEnumConstants()) {
            if (key.equals(e.getCode())) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据value获取枚举
     *
     * @param value 枚举值
     * @param clazz 枚举类
     * @return 返回枚举
     */
    public <E extends Enum<E> & IEnums> E getEnumByMessage(String value, Class<E> clazz) {
        for (E e : clazz.getEnumConstants()) {
            if (value.equals(e.getMessage())) {
                return e;
            }
        }
        return null;
    }
}
