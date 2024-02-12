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
        // 如果是存储桶，则抛出异常
        if (location.isBucket()) {
            throw new FileNotFoundException("Cannot open an output stream to a bucket");
        }
        // 如果对象存在，则抛出异常
        if (oss.doesObjectExist(location.getBucketName(), location.getFileName())) {
            throw new FileNotFoundException("The specified key already exists");
        }

        // 创建管道输入流
        final PipedInputStream inputStream = new PipedInputStream();
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
        // 如果是存储桶，则判断存储桶是否存在
        if (location.isBucket()) {
            // 判断存储桶是否存在
            return oss.doesBucketExist(location.getBucketName());
        }
        // 判断对象是否存在
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
        // 抛出异常
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
        // 如果对象存在，则返回对象的内容长度
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
        // 如果对象存在，则返回对象的最后修改时间
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
        // 创建相对路径的存储位置
        OssStorageLocation relativeLocation = OssStorageLocation.ofLocation(relativePath);
        // 返回相对路径的资源
        return new OssStorageResource(oss, ossTaskExecutor, relativeLocation);
    }

    /**
     * 获取文件名
     *
     * @return 文件名
     */
    @Override
    public String getFilename() {
        // 如果是存储桶，则返回存储桶名称
        if (location.isBucket()) {
            return location.getBucketName();
        }
        // 返回文件名称
        return location.getFileName();
    }

    /**
     * 获取描述
     *
     * @return 描述
     */
    @Override
    public String getDescription() {
        // 返回存储位置的字符串表示
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
        // 如果对象存在，则返回对象的输入流
        if (getOssObject() != null) {
            return getOssObject().getObjectContent();
        }
        return null;
    }
}
