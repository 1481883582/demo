# 知识点
## Java基础
## 多线程
## JVM
### 类加载
#### 双亲委派
```text
BootstrapClassLoader  是启动类加载器，由 C 语言实现，用来加载 JVM 启动时所需要的核心类，比如rt.jar、resources.jar等。
ExtClassLoader  是扩展类加载器，用来加载\jre\lib\ext目录下 JAR 包。
AppClassLoader  是系统类加载器，用来加载 classpath 下的类，应用程序默认用它来加载类。
自定义类加载器 用来加载自定义路径下的类。
#### 打破双亲委派两种
##### [Tomcat打破双亲委派](https://github.com/1481883582/demo/blob/master/resources/JVM/Tomcat打破双亲委派.md)
##### JDBC中的DriverManager打破双亲委派
## 设计模式
