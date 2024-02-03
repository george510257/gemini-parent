package com.gls.gemini.starter.web.support;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Request 包装类
 * 用于解决流只能读取一次的问题
 */
@Slf4j
public class CachingRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 请求体
     */
    private final byte[] requestBody;

    /**
     * 构造函数
     *
     * @param request 请求
     * @throws IOException IO异常
     */
    public CachingRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 读取请求体
        this.requestBody = IoUtil.readBytes(request.getInputStream());
        log.info("RequestWrapper requestBody: {}", this.getRequestBody());
    }

    /**
     * 获取请求体
     *
     * @return 请求体
     */
    public String getRequestBody() {
        // 将请求体转换为字符串
        return StrUtil.str(requestBody, this.getCharacterEncoding());
    }

    /**
     * 获取请求体
     *
     * @return 请求体
     * @throws IOException IO异常
     */
    @Override
    public BufferedReader getReader() throws IOException {
        // 将请求体转换为 BufferedReader
        return new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
    }

    /**
     * 获取输入流
     *
     * @return 输入流
     * @throws IOException IO异常
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 将请求体转换为 ServletInputStream
        return new RequestBodyInputStream(requestBody);
    }
}
