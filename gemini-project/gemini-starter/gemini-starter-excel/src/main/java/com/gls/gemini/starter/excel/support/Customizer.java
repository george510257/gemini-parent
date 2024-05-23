package com.gls.gemini.starter.excel.support;

/**
 * 定制器
 *
 * @param <T> 定制对象
 */
@FunctionalInterface
public interface Customizer<T> {
    /**
     * 默认定制器
     *
     * @param <T> 定制对象
     * @return 定制器
     */
    static <T> Customizer<T> withDefaults() {
        return (t) -> {
        };
    }

    /**
     * 定制
     *
     * @param t 定制对象
     */
    void customize(T t);
}
