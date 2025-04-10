# 使用官方的 OpenJDK 作为基础镜像
FROM openjdk:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将构建好的 JAR 文件复制到镜像中
COPY target/hm-dianping-0.0.1-SNAPSHOT.jar /app/app.jar

# 暴露应用运行的端口（例如 Spring Boot 默认的 8080 端口）
EXPOSE 8083

# 设置容器启动时运行的命令
ENTRYPOINT ["java", "-jar", "app.jar"]