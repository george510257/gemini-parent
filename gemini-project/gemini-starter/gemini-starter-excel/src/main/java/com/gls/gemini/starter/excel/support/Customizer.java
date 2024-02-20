package com.gls.gemini.starter.excel.support;

@FunctionalInterface
public interface Customizer<T> {
    static <T> Customizer<T> withDefaults() {
        return (t) -> {
        };
    }

    void customize(T t);
}
