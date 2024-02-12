package com.gls.gemini.starter.oss.support;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
public class OssTemplate {

    @Resource
    private AmazonS3 amazonS3;

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    public void createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket(bucketName);
        }
    }

    /**
     * 获取全部bucket
     *
     * @return bucket列表
     */
    public List<Bucket> getALlBucket() {
        return amazonS3.listBuckets();
    }

    /**
     * 获取bucket
     *
     * @param bucketName bucket名称
     * @return bucket 信息
     */
    public Bucket getBucket(String bucketName) {
        return amazonS3.listBuckets().stream().filter(bucket -> bucket.getName().equals(bucketName)).findFirst().orElse(null);
    }

    /**
     * 删除bucket
     *
     * @param bucketName bucket名称
     */
    public void deleteBucket(String bucketName) {
        if (amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.deleteBucket(bucketName);
        }
    }

    /**
     * 根据文件路径查询文件
     *
     * @param bucketName bucket名称
     * @param path       文件路径
     * @return 文件列表
     */
    public List<S3ObjectSummary> getALlObject(String bucketName, String path) {
        ObjectListing objectListing = amazonS3.listObjects(bucketName, path);
        return objectListing.getObjectSummaries();
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expireTime 过期时间
     * @param method     请求方法 GET:下载 PUT:上传
     * @return 文件外链
     */
    public String getObjectUrl(String bucketName, String objectName, Date expireTime, HttpMethod method) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName);
        request.setMethod(method);
        request.setExpiration(expireTime);
        return amazonS3.generatePresignedUrl(request).toString();
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expireTime 过期时间
     * @param method     请求方法 GET:下载 PUT:上传
     * @return 文件外链
     */
    public String getObjectUrl(String bucketName, String objectName, Duration expireTime, HttpMethod method) {
        // 过期时间
        Date expireDate = new Date(System.currentTimeMillis() + expireTime.toMillis());
        return this.getObjectUrl(bucketName, objectName, expireDate, method);
    }

    /**
     * 获取文件外链, 只用于下载
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expireTime 过期时间
     * @return 文件外链
     */
    public String getObjectUrl(String bucketName, String objectName, Duration expireTime) {
        return this.getObjectUrl(bucketName, objectName, expireTime, HttpMethod.GET);
    }

    /**
     * 获取文件外链, 只用于下载
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expireTime 过期时间
     * @return 文件外链
     */
    public String getObjectUrl(String bucketName, String objectName, Date expireTime) {
        return this.getObjectUrl(bucketName, objectName, expireTime, HttpMethod.GET);
    }

    /**
     * 获取文件外链, 只用于上传
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expireTime 过期时间
     * @return 文件外链
     */
    public String putObjectUrl(String bucketName, String objectName, Duration expireTime) {
        return this.getObjectUrl(bucketName, objectName, expireTime, HttpMethod.PUT);
    }

    /**
     * 获取文件外链, 只用于上传
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expireTime 过期时间
     * @return 文件外链
     */
    public String putObjectUrl(String bucketName, String objectName, Date expireTime) {
        return this.getObjectUrl(bucketName, objectName, expireTime, HttpMethod.PUT);
    }

    /**
     * 获取文件url
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 文件url
     */
    public String getObjectUrl(String bucketName, String objectName) {
        return amazonS3.getUrl(bucketName, objectName).toString();
    }

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 文件
     */
    public S3Object getObject(String bucketName, String objectName) {
        return amazonS3.getObject(bucketName, objectName);
    }

    /**
     * 上传文件
     *
     * @param bucketName    bucket名称
     * @param objectName    文件名称
     * @param stream        文件流
     * @param contentLength 文件大小
     * @param contentType   文件类型
     */
    public void putObject(String bucketName, String objectName, InputStream stream, Long contentLength, String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
        metadata.setContentType(contentType);
        PutObjectRequest request = new PutObjectRequest(bucketName, objectName, stream, metadata);
        request.getRequestClientOptions().setReadLimit(contentLength.intValue() + 1);
        amazonS3.putObject(request);
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     * @throws IOException IO异常
     */
    public void putObject(String bucketName, String objectName, InputStream stream) throws IOException {
        this.putObject(bucketName, objectName, stream, "application/octet-stream");
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param contentType 文件类型
     * @throws IOException IO异常
     */
    public void putObject(String bucketName, String objectName, InputStream stream, String contentType) throws IOException {
        this.putObject(bucketName, objectName, stream, (long) stream.available(), contentType);
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     */
    public void deleteObject(String bucketName, String objectName) {
        amazonS3.deleteObject(bucketName, objectName);
    }
}
