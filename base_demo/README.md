# 基础Demo
### 解决Multipart上传冲突问题
```xml
<!--Multipart-->
<!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.4</version>
</dependency>
<!--Multipart-->
```
```java
/**
 * 解决spring 上传文件冲突问题
 */
@Configuration
public class MultipartFileConfig {

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }
}
```
```yaml
spring:
    # 排除此类  解决spring 上传冲突问题
  autoconfigure:
    exclude: org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration
```
### [Minio工具类](https://gitee.com/valuenull/java-demo/blob/master/base_demo/src/main/java/com/base/utils/MinioUtils.java)
```xml
<!-- minio -->
<dependency>
    <groupId>io.minio</groupId>
    <artifactId>minio</artifactId>
    <version>${minio.version}</version>
</dependency>
<!-- minio -->
```