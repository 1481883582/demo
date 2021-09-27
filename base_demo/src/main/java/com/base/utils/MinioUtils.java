//package com.base.utils;
//import io.minio.*;
//import io.minio.messages.Bucket;
//import io.minio.messages.Item;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import java.io.*;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.function.IntFunction;
//
///**
// * Minio工具类
// *
// * @author wh
// * @version 1.0.0
// */
//@Component
//@Slf4j
//public class MinioUtils {
//
//    @Value("${minio.endpoint}")
//    private String endpoint;
//    @Value("${minio.port}")
//    private Integer port;
//    @Value("${minio.accessKey}")
//    private String accessKey;
//    @Value("${minio.secretKey}")
//    private String secretKey;
//    @Value("${minio.bucketName}")
//    private String bucketName;
//
//    @Resource
//    private MinioClient minioClient;
//
//    private final static String SYSTEM_SEPARATOR = "/";
//
//    /**
//     * 判断桶是否存在
//     *
//     * @param bucketName bucket名称
//     * @return true存在，false不存在
//     */
//    public Boolean bucketExists(String bucketName) {
//        try {
//            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
//        } catch (Exception e) {
//            throw new RuntimeException("检查桶是否存在失败!", e);
//        }
//    }
//
//    /**
//     * 创建bucket
//     *
//     * @param bucketName bucket名称
//     */
//    public void createBucket(String bucketName) {
//        if (!this.bucketExists(bucketName)) {
//            try {
//                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//            } catch (Exception e) {
//                throw new RuntimeException("创建桶失败!", e);
//            }
//        }
//    }
//
//    /**
//     * 上传MultipartFile文件到全局默认文件桶中
//     *
//     * @param file 文件
//     */
//    public void put(MultipartFile file) {
//        String fileName = Objects.requireNonNull(file.getOriginalFilename());
//        // 开始上传
//        this.putMultipartFile(bucketName, fileName, file);
//
//        this.log(fileName);
//    }
//
//    /**
//     * 上传文件
//     *
//     * @param objectName  文件名
//     * @param stream      文件流
//     * @param contentType 文件类型, 例如 image/jpeg: jpg图片格式, 详细可看: https://www.runoob.com/http/http-content-type.html
//     * @return 文件url
//     */
//    public void put(String objectName, InputStream stream, String contentType) {
//        // 开始上传
//        this.putInputStream(bucketName, objectName, stream, contentType);
//
//        this.log(objectName);
//    }
//
//    /**
//     * 上传bytes文件
//     *
//     * @param objectName  文件名
//     * @param bytes       字节
//     * @param contentType 文件类型, 例如 image/jpeg: jpg图片格式, 详细可看: https://www.runoob.com/http/http-content-type.html
//     * @return 文件url
//     */
//    public void put(String objectName, byte[] bytes, String contentType) {
//        // 开始上传
//        this.putBytes(bucketName, objectName, bytes, contentType);
//
//        this.log(objectName);
//    }
//
//    /**
//     * 上传MultipartFile文件到全局默认文件桶下的folder文件夹下
//     *
//     * @param objectName 文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
//     * @param file       文件
//     */
//    public void put(String objectName, MultipartFile file) {
//        // 开始上传
//        this.putMultipartFile(bucketName, objectName, file);
//
//        this.log(objectName);
//    }
//
//    /**
//     * 上传MultipartFile文件到指定的文件桶下
//     *
//     * @param bucketName 桶名称
//     * @param objectName 文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
//     * @param file       文件
//     */
//    public void put(String bucketName, String objectName, MultipartFile file) {
//        // 先创建桶
//        this.createBucket(bucketName);
//        // 开始上传
//        this.putMultipartFile(bucketName, objectName, file);
//
//        this.log(bucketName, objectName);
//    }
//
//    /**
//     * 上传流到指定的文件桶下
//     *
//     * @param bucketName  桶名称
//     * @param objectName  文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
//     * @param stream      文件流
//     * @param contentType 文件类型, 例如 image/jpeg: jpg图片格式, 详细可看: https://www.runoob.com/http/http-content-type.html
//     */
//    public void put(String bucketName, String objectName, InputStream stream, String contentType) {
//        // 先创建桶
//        this.createBucket(bucketName);
//        // 开始上传
//        this.putInputStream(bucketName, objectName, stream, contentType);
//
//        this.log(bucketName, objectName);
//    }
//
//    /**
//     * 上传流到指定的文件桶下
//     *
//     * @param bucketName  桶名称
//     * @param objectName  文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
//     * @param bytes       字节
//     * @param contentType 文件类型, 例如 image/jpeg: jpg图片格式, 详细可看: https://www.runoob.com/http/http-content-type.html
//     */
//    public void put(String bucketName, String objectName, byte[] bytes, String contentType) {
//        // 先创建桶
//        this.createBucket(bucketName);
//        // 开始上传
//        this.putBytes(bucketName, objectName, bytes, contentType);
//
//        this.log(bucketName, objectName);
//    }
//
//    /**
//     * 上传File文件到默认桶下
//     *
//     * @param objectName  文件名
//     * @param file        文件
//     * @param contentType 文件类型, 例如 image/jpeg: jpg图片格式, 详细可看: https://www.runoob.com/http/http-content-type.html
//     */
//    public void put(String objectName, File file, String contentType) {
//        // 开始上传
//        this.putFile(bucketName, objectName, file, contentType);
//
//        this.log(objectName);
//    }
//
//    /**
//     * 上传File文件
//     *
//     * @param bucketName  文件桶
//     * @param objectName  文件名
//     * @param file        文件
//     * @param contentType 文件类型, 例如 image/jpeg: jpg图片格式, 详细可看: https://www.runoob.com/http/http-content-type.html
//     */
//    public void put(String bucketName, String objectName, File file, String contentType) {
//        // 先创建桶
//        this.createBucket(bucketName);
//
//        // 开始上传
//        this.putFile(bucketName, objectName, file, contentType);
//
//        this.log(bucketName, objectName);
//    }
//
//    /**
//     * 打印日志
//     * @param bucketName    bucket名称
//     * @param fileName      文件名称
//     */
//    public void log(String bucketName, String fileName){
//        log.info("上传路径：{}", endpoint
//                + SYSTEM_SEPARATOR
//                + bucketName
//                + SYSTEM_SEPARATOR + fileName
//        );
//    }
//
//    /**
//     * 打印日志
//     * @param fileName  文件名称
//     */
//    public void log(String fileName){
//        log.info("上传路径：{}", endpoint
//                + SYSTEM_SEPARATOR
//                + bucketName
//                + SYSTEM_SEPARATOR + fileName
//        );
//    }
//
//    /**
//     * 判断文件是否存在
//     *
//     * @param objectName 文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
//     * @return true存在, 反之
//     */
//    public Boolean checkFileIsExist(String objectName) {
//        return this.checkFileIsExist(bucketName, objectName);
//    }
//
//    /**
//     * 判断文件夹是否存在
//     *
//     * @param folderName 文件夹名称
//     * @return true存在, 反之
//     */
//    public Boolean checkFolderIsExist(String folderName) {
//        return this.checkFolderIsExist(bucketName, folderName);
//    }
//
//    /**
//     * 判断文件是否存在
//     *
//     * @param bucketName 桶名称
//     * @param objectName 文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
//     * @return true存在, 反之
//     */
//    public Boolean checkFileIsExist(String bucketName, String objectName) {
//        try {
//            minioClient.statObject(
//                    StatObjectArgs.builder().bucket(bucketName).object(objectName).build()
//            );
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 判断文件夹是否存在
//     *
//     * @param bucketName 桶名称
//     * @param folderName 文件夹名称
//     * @return true存在, 反之
//     */
//    public Boolean checkFolderIsExist(String bucketName, String folderName) {
//        try {
//            Iterable<Result<Item>> results = minioClient.listObjects(
//                    ListObjectsArgs
//                            .builder()
//                            .bucket(bucketName)
//                            .prefix(folderName)
//                            .recursive(false)
//                            .build());
//            for (Result<Item> result : results) {
//                Item item = result.get();
//                if (item.isDir() && folderName.equals(item.objectName())) {
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 根据文件全路径获取文件流
//     *
//     * @param objectName 文件名称
//     * @return 文件流
//     */
//    public InputStream getObject(String objectName) {
//        return this.getObject(bucketName, objectName);
//    }
//
//    /**
//     * 根据文件桶和文件全路径获取文件流
//     *
//     * @param bucketName 桶名称
//     * @param objectName 文件名
//     * @return 文件流
//     */
//    public InputStream getObject(String bucketName, String objectName) {
//        try {
//            return minioClient
//                    .getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
//        } catch (Exception e) {
//            throw new RuntimeException("根据文件名获取流失败!", e);
//        }
//    }
//
//    /**
//     * 根据url获取文件流
//     *
//     * @param url 文件URL
//     * @return 文件流
//     */
//    public InputStream getObjectByUrl(String url) {
//        try {
//            return new URL(url).openStream();
//        } catch (IOException e) {
//            throw new RuntimeException("根据URL获取流失败!", e);
//        }
//    }
//
//    /**
//     * 获取全部bucket
//     *
//     * @return 所有桶信息
//     */
//    public List<Bucket> getAllBuckets() {
//        try {
//            return minioClient.listBuckets();
//        } catch (Exception e) {
//            throw new RuntimeException("获取全部存储桶失败!", e);
//        }
//    }
//
//    /**
//     * 根据bucketName获取信息
//     *
//     * @param bucketName bucket名称
//     * @return 单个桶信息
//     */
//    public Optional<Bucket> getBucket(String bucketName) {
//        try {
//            return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
//        } catch (Exception e) {
//            throw new RuntimeException("根据存储桶名称获取信息失败!", e);
//        }
//    }
//
//    /**
//     * 根据bucketName删除信息
//     *
//     * @param bucketName bucket名称
//     */
//    public void removeBucket(String bucketName) {
//        try {
//            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
//        } catch (Exception e) {
//            throw new RuntimeException("根据存储桶名称删除桶失败!", e);
//        }
//    }
//
//    /**
//     * 删除文件
//     *
//     * @param objectName 文件名称
//     */
//    public boolean removeObject(String objectName) {
//        try {
//            this.removeObject(bucketName, objectName);
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 删除文件
//     *
//     * @param bucketName bucket名称
//     * @param objectName 文件名称
//     */
//    public boolean removeObject(String bucketName, String objectName) {
//        try {
//            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 上传MultipartFile通用方法
//     *
//     * @param bucketName 桶名称
//     * @param objectName 文件名
//     * @param file       文件
//     */
//    private void putMultipartFile(String bucketName, String objectName, MultipartFile file) {
//        InputStream stream = null;
//        try {
//            stream = file.getInputStream();
//        } catch (IOException e) {
//            throw new RuntimeException("文件流获取错误", e);
//        }
//        try {
//            minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(objectName)
//                            .contentType(file.getContentType())
//                            .stream(stream, stream.available(), -1)
//                            .build()
//            );
//        } catch (Exception e) {
//            throw new RuntimeException("文件流上传错误", e);
//        }
//    }
//
//    /**
//     * 上传InputStream通用方法
//     *
//     * @param bucketName 桶名称
//     * @param objectName 文件名
//     * @param stream     文件流
//     */
//    private void putInputStream(String bucketName, String objectName, InputStream stream, String contentType) {
//        try {
//            minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(objectName)
//                            .contentType(contentType)
//                            .stream(stream, stream.available(), -1)
//                            .build()
//            );
//        } catch (Exception e) {
//            throw new RuntimeException("文件流上传错误", e);
//        }
//    }
//
//    /**
//     * 上传 bytes 通用方法
//     *
//     * @param bucketName 桶名称
//     * @param objectName 文件名
//     * @param bytes      字节编码
//     */
//    private void putBytes(String bucketName, String objectName, byte[] bytes, String contentType) {
//        // 字节转文件流
//        InputStream stream = new ByteArrayInputStream(bytes);
//        try {
//            minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(objectName)
//                            .contentType(contentType)
//                            .stream(stream, stream.available(), -1)
//                            .build()
//            );
//        } catch (Exception e) {
//            throw new RuntimeException("文件流上传错误", e);
//        }
//    }
//
//    /**
//     * 上传 file 通用方法
//     *
//     * @param bucketName  桶名称
//     * @param objectName  文件名
//     * @param file        文件
//     * @param contentType 文件类型, 例如 image/jpeg: jpg图片格式, 详细可看: https://www.runoob.com/http/http-content-type.html
//     */
//    private void putFile(String bucketName, String objectName, File file, String contentType) {
//        try {
//            FileInputStream fileInputStream = new FileInputStream(file);
//            minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(objectName)
//                            .contentType(contentType)
//                            .stream(fileInputStream, fileInputStream.available(), -1)
//                            .build()
//            );
//        } catch (Exception e) {
//            throw new RuntimeException("文件上传错误", e);
//        }
//    }
//
//    /**
//     * 生成唯一ID
//     *
//     * @param objectName 文件名
//     * @return 唯一ID
//     */
//    public static String getFileName(String objectName) {
//        //判断文件最后一个点所在的位置
//        int lastIndexOf = objectName.lastIndexOf(".");
//        if (lastIndexOf == -1) {
//            return String.format("%s_%s", objectName, System.currentTimeMillis());
//        } else {
//            // 获取文件前缀,已最后一个点进行分割
//            String filePrefix = objectName.substring(0, objectName.lastIndexOf("."));
//            // 获取文件后缀,已最后一个点进行分割
//            String fileSuffix = objectName.substring(objectName.lastIndexOf(".") + 1);
//            // 组成唯一文件名
//            return String.format("%s_%s.%s", filePrefix, System.currentTimeMillis(), fileSuffix);
//        }
//    }
//
//    /**
//     * 获取文件信息, 如果抛出异常则说明文件不存在
//     *
//     * @param bucketName bucket名称
//     * @param objectName 文件名称
//     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
//     */
//    public StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
//        return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
//    }
//
//    /**
//     * 获取文件外链
//     *
//     * @param bucketName bucket名称
//     * @param objectName 文件名称
//     * @param expires    过期时间 <=7
//     * @return url
//     */
//    @SneakyThrows
//    public String getObjectURL(String bucketName, String objectName, Integer expires) {
//        IntFunction<Integer> integerIntFunction = (int i) -> {
//            return Math.min(i, 7);
//        };
//        return minioClient.getPresignedObjectUrl(
//                GetPresignedObjectUrlArgs.builder()
//                        .bucket(bucketName)
//                        .object(objectName)
//                        .expiry(integerIntFunction.apply(expires))
//                        .build());
//    }
//
//    /**
//     * 根据文件前置查询文件
//     *
//     * @param bucketName bucket名称
//     * @param prefix     前缀
//     * @param recursive  是否递归查询
//     * @return MinioItem 列表
//     */
//    @SneakyThrows
//    public List<Item> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
//        List<Item> list = new ArrayList<>();
//        Iterable<Result<Item>> objectsIterator = minioClient.listObjects(
//                ListObjectsArgs.builder().bucket(bucketName).prefix(prefix)
//                        .recursive(recursive).build()
//        );
//        if (objectsIterator != null) {
//            for (Result<Item> result : objectsIterator) {
//                Item item = result.get();
//                list.add(item);
//            }
//        }
//        return list;
//    }
//
//}
//
