package com.gls.gemini.starter.openapi.properties;

import com.gls.gemini.common.core.base.BaseProperties;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.callbacks.Callback;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.links.Link;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 组件属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ComponentsProperties extends BaseProperties {
    /**
     * 模式
     */
    private Map<String, Schema> schemas = null;
    /**
     * 响应
     */
    private Map<String, ApiResponse> responses = null;
    /**
     * 参数
     */
    private Map<String, Parameter> parameters = null;
    /**
     * 示例
     */
    private Map<String, Example> examples = null;
    /**
     * 请求体
     */
    private Map<String, RequestBody> requestBodies = null;
    /**
     * 头部
     */
    private Map<String, Header> headers = null;
    /**
     * 安全方案
     */
    private Map<String, SecurityScheme> securitySchemes = null;
    /**
     * 链接
     */
    private Map<String, Link> links = null;
    /**
     * 回调
     */
    private Map<String, Callback> callbacks = null;
    /**
     * 路径项
     */
    private Map<String, PathItem> pathItems;
}
