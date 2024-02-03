package com.gls.gemini.starter.web.support;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

/**
 * Request 包装类 过滤器
 * 用于解决流只能读取一次的问题
 *
 * <p>
 * 由于 requestBody 和 responseBody 分别对应的是 InputStream 和 OutputStream，由于流的特性，读取完之后就无法再被使用了。
 * 所以，需要额外缓存一次流信息。
 * </p>
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class CachingWebFilter extends OncePerRequestFilter {

    /**
     * JSON Content-Type
     */
    private static final String JSON_CONTENT_TYPE = "application/json";

    /**
     * 执行过滤器
     *
     * @param request     请求
     * @param response    响应
     * @param filterChain 过滤器链
     * @throws ServletException Servlet异常
     * @throws IOException      IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 如果是 JSON 请求，则包装请求和响应
        if (request.getContentType() != null && request.getContentType().contains(JSON_CONTENT_TYPE)) {
            // 包装请求和响应
            CachingRequestWrapper cachingRequestWrapper = new CachingRequestWrapper(request);
            ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(response);
            // 执行过滤器链
            filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);
            // 更新响应
            cachingResponseWrapper.copyBodyToResponse();
        } else {
            // 执行过滤器链
            filterChain.doFilter(request, response);
        }
    }
}