package com.gls.gemini.common.core.support;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * yaml配置文件加载工厂
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    /**
     * 创建属性源
     *
     * @param name     名称
     * @param resource 资源
     * @return PropertySource 属性源
     */
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        // 加载yaml配置文件
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        // 设置资源
        yamlPropertiesFactoryBean.setResources(resource.getResource());
        yamlPropertiesFactoryBean.afterPropertiesSet();
        // 加载配置
        Properties properties = yamlPropertiesFactoryBean.getObject();
        // 获取资源名称
        String sourceName = name != null ? name : resource.getResource().getFilename();
        // 返回属性源
        return new PropertiesPropertySource(sourceName, properties);
    }
}
