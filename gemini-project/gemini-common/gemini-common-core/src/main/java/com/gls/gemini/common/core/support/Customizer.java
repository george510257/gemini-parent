package com.gls.gemini.common.core.support;

@FunctionalInterface
public interface Customizer<T> {
    void customize(T t);
}
