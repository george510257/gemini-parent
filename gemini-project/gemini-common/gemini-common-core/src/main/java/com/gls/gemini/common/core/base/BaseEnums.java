package com.gls.gemini.common.core.base;

public interface BaseEnums<E extends Enum<E> & BaseEnums<E>> {
    /**
     * 获取编码
     *
     * @return 返回编码
     */
    Integer getKey();

    /**
     * 获取名称
     *
     * @return 返回名称
     */
    String getValue();

    /**
     * 根据key获取枚举
     *
     * @param key   枚举key
     * @param clazz 枚举类
     * @return 返回枚举
     */
    default E getEnumByKey(Integer key, Class<E> clazz) {
        for (E e : clazz.getEnumConstants()) {
            if (key.equals(e.getKey())) {
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
    default E getEnumByValue(String value, Class<E> clazz) {
        for (E e : clazz.getEnumConstants()) {
            if (value.equals(e.getValue())) {
                return e;
            }
        }
        return null;
    }
}
