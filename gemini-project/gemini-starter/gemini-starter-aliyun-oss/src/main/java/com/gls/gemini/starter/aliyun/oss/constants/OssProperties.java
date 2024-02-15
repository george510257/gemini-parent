package com.gls.gemini.starter.aliyun.oss.constants;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.gls.gemini.common.core.base.BaseProperties;
import com.gls.gemini.common.core.constant.CommonConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * oss 动态配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = CommonConstants.BASE_PROPERTIES_PREFIX + ".aliyun.oss")
public class OssProperties extends BaseProperties {
    /**
     * 安全配置
     */
    private Client client = new Client();
    /**
     * 存储空间名称
     */
    private String bucketName;
    /**
     * 文件路径前缀
     */
    private String filePrefix;
    /**
     * 线程池配置
     */
    private ThreadPool threadPool = new ThreadPool();

    @Data
    public static class Client implements Serializable {
        /**
         * 阿里云OSS服务的Endpoint
         */
        private String endpoint;
        /**
         * 授权模式
         */
        private Mode mode;
        /**
         * token
         */
        private Token token = new Token();
        /**
         * stsToken
         */
        private StsToken stsToken = new StsToken();
        /**
         * 配置
         */
        @NestedConfigurationProperty
        private ClientBuilderConfiguration config = new ClientBuilderConfiguration();

        public enum Mode {
            /**
             * 使用AK/SK
             */
            AK_SK,
            /**
             * 使用STS
             */
            STS
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class StsToken extends Token {
            /**
             * 安全令牌
             */
            private String securityToken;
        }

        @Data
        public static class Token implements Serializable {
            /**
             * 访问密钥
             */
            private String accessKey;
            /**
             * 安全密钥
             */
            private String secretKey;
        }
    }

    @Data
    public static class ThreadPool implements Serializable {
        /**
         * 核心线程数
         */
        private int corePoolSize = Runtime.getRuntime().availableProcessors();
        /**
         * 最大线程数
         */
        private int maximumPoolSize = 128;
        /**
         * 空闲线程存活时间
         */
        private long keepAliveTime = 60;
        /**
         * 时间单位
         */
        private TimeUnit unit = TimeUnit.SECONDS;
        /**
         * 阻塞队列
         */
        private int capacity = 100;
    }
}
