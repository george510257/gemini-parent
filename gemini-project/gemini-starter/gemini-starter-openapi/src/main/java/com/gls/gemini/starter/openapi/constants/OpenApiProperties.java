package com.gls.gemini.starter.openapi.constants;

import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import io.swagger.v3.oas.models.callbacks.Callback;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.links.Link;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * swagger配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.BASE_PROPERTIES_PREFIX + ".open-api")
public class OpenApiProperties extends BaseProperties {
    /**
     * openapi版本
     */
    private String openapi = "3.0.1";
    /**
     * 配置信息
     */
    private Info info = new Info();
    /**
     * 外部文档
     */
    private ExternalDocs externalDocs = new ExternalDocs();
    /**
     * 服务地址
     */
    private List<Server> servers = new ArrayList<>();

    private List<SecurityRequirement> security = new ArrayList<>();
    /**
     *
     */
    private Components components = new Components();

    /**
     * 配置信息
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Info extends BaseProperties {
        /**
         * 标题
         */
        private String title = null;
        /**
         * 描述
         */
        private String description = null;
        /**
         * 服务条款
         */
        private String termsOfService = null;
        /**
         * 联系人
         */
        private Contact contact = new Contact();
        /**
         * 许可证
         */
        private License license = new License();
        /**
         * 版本
         */
        private String version = null;
        /**
         * 摘要
         */
        private String summary = null;

        /**
         * 联系人属性
         */
        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class Contact extends BaseProperties {

            /**
             * 联系人名称
             */
            private String name = null;
            /**
             * 联系人url
             */
            private String url = null;
            /**
             * 联系人email
             */
            private String email = null;
        }

        /**
         * 许可证属性
         */
        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class License extends BaseProperties {
            /**
             * 许可证名称
             */
            private String name = null;
            /**
             * 许可证url
             */
            private String url = null;
            /**
             * 许可证identifier
             */
            private String identifier = null;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ExternalDocs extends BaseProperties {
        /**
         * 外部文档描述
         */
        private String description = null;
        /**
         * 外部文档url
         */
        private String url = null;
    }

    /**
     * 服务器属性
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Server extends BaseProperties {
        /**
         * 服务器url
         */
        private String url = null;
        /**
         * 服务器描述
         */
        private String description = null;

    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class Components extends BaseProperties {

        private Map<String, Schema> schemas = null;
        private Map<String, ApiResponse> responses = null;
        private Map<String, Parameter> parameters = null;
        private Map<String, Example> examples = null;
        private Map<String, RequestBody> requestBodies = null;
        private Map<String, Header> headers = null;
        private Map<String, SecurityScheme> securitySchemes = null;
        private Map<String, Link> links = null;
        private Map<String, Callback> callbacks = null;
    }
}
