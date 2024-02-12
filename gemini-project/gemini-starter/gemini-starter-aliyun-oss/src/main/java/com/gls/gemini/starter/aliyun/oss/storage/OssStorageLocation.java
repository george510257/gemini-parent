package com.gls.gemini.starter.aliyun.oss.storage;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.net.URI;

/**
 * oss存储位置
 */
@Data
public class OssStorageLocation {
    /**
     * 协议格式
     */
    private static final String PROTOCOL_FORMAT = "oss://%s/%s";
    /**
     * 存储桶名称
     */
    private final String bucketName;
    /**
     * 文件名称
     */
    private final String fileName;
    /**
     * URI 格式
     */
    private final URI uri;

    /**
     * 构造函数
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     */
    private OssStorageLocation(String bucketName, String fileName) {
        // 如果存储桶名称为空，则抛出异常
        if (StrUtil.isBlank(bucketName)) {
            throw new IllegalArgumentException("Bucket name must not be blank");
        }
        // 如果文件名称为空，则设置为空字符串
        if (StrUtil.isBlank(fileName)) {
            fileName = "";
        }
        // 如果文件名称不为空且以/开头，则去掉/
        if (StrUtil.isNotBlank(fileName) && fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }
        this.bucketName = bucketName;
        this.fileName = fileName;
        this.uri = URI.create(String.format(PROTOCOL_FORMAT, bucketName, fileName));
    }

    /**
     * 创建存储位置
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return 存储位置
     */
    public static OssStorageLocation of(String bucketName, String fileName) {
        return new OssStorageLocation(bucketName, fileName);
    }

    /**
     * 创建存储位置
     *
     * @param bucketName 存储桶名称
     * @return 存储位置
     */
    public static OssStorageLocation of(String bucketName) {
        return of(bucketName, "");
    }

    /**
     * 创建存储位置
     *
     * @param uri URI 格式
     * @return 存储位置
     */
    public static OssStorageLocation of(URI uri) {
        // 如果URI为空，则抛出异常
        if (uri == null) {
            throw new IllegalArgumentException("URI must not be null");
        }
        // 如果URI的协议不是oss，则抛出异常
        if (!"oss".equals(uri.getScheme())) {
            throw new IllegalArgumentException("URI scheme must be oss");
        }
        // 如果URI的主机为空，则抛出异常
        if (StrUtil.isBlank(uri.getAuthority())) {
            throw new IllegalArgumentException("URI authority must not be blank");
        }
        // 返回存储位置
        return of(uri.getAuthority(), uri.getPath());
    }

    /**
     * 创建存储位置
     *
     * @param location 存储位置
     * @return 存储位置
     */
    public static OssStorageLocation ofLocation(String location) {
        return of(URI.create(location));
    }

    /**
     * 是否是存储桶
     *
     * @return 是否是存储桶
     */
    public boolean isBucket() {
        return StrUtil.isBlank(fileName);
    }

    /**
     * 是否是文件
     *
     * @return 是否是文件
     */
    public boolean isFile() {
        // 如果文件名称不为空且不以/结尾，则是文件
        return StrUtil.isNotBlank(fileName) && !fileName.endsWith("/") ;
    }

    /**
     * 是否是目录
     *
     * @return 是否是目录
     */
    public boolean isDirectory() {
        return !isBucket() && !isFile();
    }

    /**
     * 获取文件全路径
     *
     * @return 文件全路径
     */
    @Override
    public String toString() {
        return uri.toString();
    }
}
