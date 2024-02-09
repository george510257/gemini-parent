package com.gls.gemini.starter.aliyun.oss.support;

import com.aliyun.oss.OSSClient;
import com.gls.gemini.starter.aliyun.oss.constants.OssProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Slf4j
@Component
public class OssHelper {

    @Resource
    private OSSClient ossClient;

    @Resource
    private OssProperties ossProperties;

    /**
     * 创建bucket
     */
    public boolean createBucket() {
        if (!ossClient.doesBucketExist(ossProperties.getBucketName())) {
            ossClient.createBucket(ossProperties.getBucketName());
            log.info("oss bucket {} create success", ossProperties.getBucketName());
            return true;
        }
        return false;
    }

    /**
     * 删除bucket
     */
    public boolean deleteBucket() {
        if (ossClient.doesBucketExist(ossProperties.getBucketName())) {
            ossClient.deleteBucket(ossProperties.getBucketName());
            log.info("oss bucket {} delete success", ossProperties.getBucketName());
            return true;
        }
        return false;
    }

    /**
     * 上传文件
     */
    public String upload(String path, byte[] bytes) {
        ossClient.putObject(ossProperties.getBucketName(), path, new ByteArrayInputStream(bytes));
        return path;
    }

}
