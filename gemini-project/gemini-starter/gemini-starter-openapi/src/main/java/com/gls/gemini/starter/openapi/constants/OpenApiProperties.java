package com.gls.gemini.starter.openapi.constants;

import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.BASE_PROPERTIES_PREFIX + ".open-api")
public class OpenApiProperties extends BaseProperties {

    /**
     * 配置信息
     */
    private Info info = new Info();
    /**
     * 服务地址
     */
    private List<Server> servers = new ArrayList<>();


    /**
     * 配置信息
     */
    @Data
    public static class Info implements Serializable {
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
        public static class Contact implements Serializable {

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
        public static class License implements Serializable {
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


    /**
     * 服务器属性
     */
    @Data
    public static class Server implements Serializable {
        /**
         * 服务器url
         */
        private String url = null;
        /**
         * 服务器描述
         */
        private String description = null;

    }
}
