package com.gls.gemini.starter.aliyun.oss.support;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectMetadata;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class OssTemplate {

    @Resource
    private OSSClient ossClient;

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    public void createBucket(String bucketName) {
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
        }
    }

    /**
     * 获取全部bucket
     *
     * @return bucket列表
     */
    public List<Bucket> getALlBucket() {
        return ossClient.listBuckets();
    }

    /**
     * 获取bucket
     *
     * @param bucketName bucket名称
     * @return bucket 信息
     */
    public Bucket getBucket(String bucketName) {
        return ossClient.listBuckets().stream().filter(bucket -> bucket.getName().equals(bucketName)).findFirst().orElse(null);
    }

    /**
     * 删除bucket
     *
     * @param bucketName bucket名称
     */
    public void deleteBucket(String bucketName) {
        if (ossClient.doesBucketExist(bucketName)) {
            ossClient.deleteBucket(bucketName);
        }
    }

    /**
     * 根据文件路径获取文件列表
     *
     * @param bucketName bucket名称
     * @param prefix     文件路径
     * @return 文件列表
     */
    public List<OSSObjectSummary> listFile(String bucketName, String prefix) {
        return ossClient.listObjects(bucketName, prefix).getObjectSummaries();
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param key        文件路径
     * @param expiration 过期时间
     * @param method     请求方法 GET:下载 PUT:上传
     * @return 文件外链
     */
    public String getObjectUrl(String bucketName, String key, Date expiration, HttpMethod method) {
        return ossClient.generatePresignedUrl(bucketName, key, expiration, method).toString();
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param key        文件路径
     * @param expiration 过期时间
     * @param method     请求方法 GET:下载 PUT:上传
     * @return 文件外链
     */
    public String getObjectUrl(String bucketName, String key, Duration expiration, HttpMethod method) {
        Date date = new Date(System.currentTimeMillis() + expiration.toMillis());
        return this.getObjectUrl(bucketName, key, date, method);
    }

    /**
     * 获取文件外链, 只用于下载
     *
     * @param bucketName bucket名称
     * @param key        文件路径
     * @param expiration 过期时间
     * @return 文件外链
     */
    public String getObjectUrl(String bucketName, String key, Duration expiration) {
        return this.getObjectUrl(bucketName, key, expiration, HttpMethod.GET);
    }

    /**
     * 获取文件外链, 只用于下载
     *
     * @param bucketName bucket名称
     * @param key        文件路径
     * @param expiration 过期时间
     * @return 文件外链
     */
    public String getObjectUrl(String bucketName, String key, Date expiration) {
        return this.getObjectUrl(bucketName, key, expiration, HttpMethod.GET);
    }

    /**
     * 获取文件外链, 只用于上传
     *
     * @param bucketName bucket名称
     * @param key        文件路径
     * @param expiration 过期时间
     * @return 文件外链
     */
    public String putObjectUrl(String bucketName, String key, Duration expiration) {
        return this.getObjectUrl(bucketName, key, expiration, HttpMethod.PUT);
    }

    /**
     * 获取文件外链, 只用于上传
     *
     * @param bucketName bucket名称
     * @param key        文件路径
     * @param expiration 过期时间
     * @return 文件外链
     */
    public String putObjectUrl(String bucketName, String key, Date expiration) {
        return this.getObjectUrl(bucketName, key, expiration, HttpMethod.PUT);
    }

    /**
     * 获取文件url
     *
     * @param bucketName bucket名称
     * @param key        文件路径
     * @return 文件url
     */
    public String getObjectUrl(String bucketName, String key) {
        return ossClient.getObject(bucketName, key).getResponse().getUri();
    }

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param key        文件路径
     * @return 文件
     */
    public OSSObject getObject(String bucketName, String key) {
        return ossClient.getObject(bucketName, key);
    }

    /**
     * 上传文件
     *
     * @param bucketName    bucket名称
     * @param key           文件路径
     * @param stream        文件流
     * @param contentLength 文件大小
     * @param contentType   文件类型
     */
    public void putObject(String bucketName, String key, InputStream stream, long contentLength, String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
        metadata.setContentType(contentType);
        ossClient.putObject(bucketName, key, stream, metadata);
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param key         文件路径
     * @param stream      文件流
     * @param contentType 文件类型
     * @throws IOException 文件流异常
     */
    public void putObject(String bucketName, String key, InputStream stream, String contentType) throws IOException {
        this.putObject(bucketName, key, stream, stream.available(), contentType);
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param key        文件路径
     * @param stream     文件流
     * @throws IOException 文件流异常
     */
    public void putObject(String bucketName, String key, InputStream stream) throws IOException {
        this.putObject(bucketName, key, stream, "application/octet-stream");
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param key        文件路径
     */
    public void deleteObject(String bucketName, String key) {
        ossClient.deleteObject(bucketName, key);
    }
}
