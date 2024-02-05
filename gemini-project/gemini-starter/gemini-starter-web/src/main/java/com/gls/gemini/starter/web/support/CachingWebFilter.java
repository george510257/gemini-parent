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

    @Override
    public void destroy() {
        log.info("CachingWebFilter destroy");
    }

    @Override
    protected void initFilterBean() throws ServletException {
        log.info("CachingWebFilter initFilterBean");
    }

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
            // 打印 request
            this.printRequest(cachingRequestWrapper);
            // 执行过滤器链
            filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);
            // 打印 response
            this.printResponse(cachingResponseWrapper);
            // 更新响应
            cachingResponseWrapper.copyBodyToResponse();
        } else {
            // 执行过滤器链
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 打印请求
     *
     * @param response 响应
     */
    private void printResponse(ContentCachingResponseWrapper response) {
        // 响应体
        String responseBody = new String(response.getContentAsByteArray());
        log.info("Response: responseBody:{}", responseBody);
    }

    /**
     * 打印请求
     *
     * @param request 请求
     */
    private void printRequest(HttpServletRequest request) {
        // ip
        String ip = request.getRemoteAddr();
        // 请求方法
        String method = request.getMethod();
        // 请求路径
        String path = request.getRequestURI();
        // 请求参数
        String queryString = request.getQueryString();
        // 请求体
        String requestBody = ((CachingRequestWrapper) request).getRequestBody();
        log.info("Request: ip:{}, method:{}, path:{}, queryString:{}, requestBody:{}", ip, method, path, queryString, requestBody);
    }
}
