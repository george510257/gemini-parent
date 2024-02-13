package com.gls.gemini.starter.excel.customizer;

@FunctionalInterface
public interface Customizer<T> {
    void customize(T t);
}
