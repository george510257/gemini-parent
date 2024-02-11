package com.gls.gemini.starter.aliyun.oss.storage;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutorService;

/**
 * oss 资源适配
 */
@Slf4j
@RequiredArgsConstructor
public class OssStorageResource implements WritableResource {
    /**
     * OSS 客户端
     */
    private final OSS oss;
    /**
     * OSS 任务执行器
     */
    private final ExecutorService ossTaskExecutor;
    /**
     * OSS 存储位置
     */
    private final OssStorageLocation location;

    /**
     * 获取输出流
     *
     * @return 输出流
     * @throws IOException IO异常
     */
    @Override
    public OutputStream getOutputStream() throws IOException {
        // 创建管道输入流
        PipedInputStream inputStream = new PipedInputStream();
        // 创建管道输出流
        final OutputStream outputStream = new PipedOutputStream(inputStream);
        // 执行 OSS 任务
        ossTaskExecutor.execute(() -> {
            try {
                // 写入 OSS 对象
                oss.putObject(location.getBucketName(), location.getFileName(), inputStream);
            } catch (Exception e) {
                log.error("Failed to write to OSS", e);
            }
        });

        return outputStream;
    }

    /**
     * 是否存在
     *
     * @return 是否存在
     */
    @Override
    public boolean exists() {
        if (location.isBucket()) {
            return oss.doesBucketExist(location.getBucketName());
        }
        return oss.doesObjectExist(location.getBucketName(), location.getFileName());
    }

    /**
     * 获取 OSS 对象
     *
     * @return OSS 对象
     */
    public OSSObject getOssObject() {
        return oss.getObject(location.getBucketName(), location.getFileName());
    }

    /**
     * 获取 OSS Bucket
     *
     * @return OSS Bucket
     */
    public Bucket getBucket() {
        return oss.getBucketInfo(location.getBucketName()).getBucket();
    }

    /**
     * 获取 OSS URL
     *
     * @return OSS URL
     * @throws IOException IO异常
     */
    @Override
    public URL getURL() throws IOException {
        return this.location.getUri().toURL();
    }

    /**
     * 获取 URI
     *
     * @return URI 对象
     * @throws IOException IO异常
     */
    @Override
    public URI getURI() throws IOException {
        return this.location.getUri();
    }

    /**
     * 获取文件
     *
     * @return 文件
     * @throws IOException IO异常
     */
    @Override
    public File getFile() throws IOException {
        throw new UnsupportedOperationException("The OSS object cannot be resolved to a File");
    }

    /**
     * 获取内容长度
     *
     * @return 内容长度
     * @throws IOException IO异常
     */
    @Override
    public long contentLength() throws IOException {
        if (getOssObject() != null) {
            return getOssObject().getObjectMetadata().getContentLength();
        }
        return 0;
    }

    /**
     * 获取最后修改时间
     *
     * @return 最后修改时间
     * @throws IOException IO异常
     */
    @Override
    public long lastModified() throws IOException {
        if (getOssObject() != null) {
            return getOssObject().getObjectMetadata().getLastModified().getTime();
        }
        return 0;
    }

    /**
     * 创建相对路径
     *
     * @param relativePath 相对路径
     * @return 资源
     * @throws IOException IO异常
     */
    @Override
    public Resource createRelative(String relativePath) throws IOException {
        OssStorageLocation relativeLocation = OssStorageLocation.ofLocation(relativePath);
        return new OssStorageResource(oss, ossTaskExecutor, relativeLocation);
    }

    /**
     * 获取文件名
     *
     * @return 文件名
     */
    @Override
    public String getFilename() {
        return location.isBucket() ? location.getBucketName() : location.getFileName();
    }

    /**
     * 获取描述
     *
     * @return 描述
     */
    @Override
    public String getDescription() {
        return location.toString();
    }

    /**
     * 获取输入流
     *
     * @return 输入流
     * @throws IOException IO异常
     */
    @Override
    public InputStream getInputStream() throws IOException {
        if (getOssObject() != null) {
            return getOssObject().getObjectContent();
        }
        return null;
    }
}
