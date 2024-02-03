package com.gls.gemini.starter.web.support;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * RequestBodyInputStream
 * 用于解决流只能读取一次的问题
 */
public class RequestBodyInputStream extends ServletInputStream {
    /**
     * 代理
     */
    private final ByteArrayInputStream delegate;

    /**
     * 构造函数
     *
     * @param body 请求体
     */
    public RequestBodyInputStream(byte[] body) {
        this.delegate = new ByteArrayInputStream(body);
    }

    /**
     * 是否已经读取完毕
     *
     * @return 是否已经读取完毕
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    /**
     * 是否准备好
     *
     * @return 是否准备好
     */
    @Override
    public boolean isReady() {
        return true;
    }

    /**
     * 设置读取监听器
     *
     * @param readListener 读取监听器
     */
    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

    /**
     * 读取
     *
     * @return 读取
     * @throws IOException IO异常
     */
    @Override
    public int read() throws IOException {
        return this.delegate.read();
    }
}
