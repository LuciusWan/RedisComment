package com.hmdp.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Component
public class RabbitMQProperties {
    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;


    public RabbitMQProperties() {
    }

    public RabbitMQProperties(String host, int port, String username, String password, String virtualHost) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.virtualHost = virtualHost;
    }

    /**
     * 获取
     * @return host
     */
    public String getHost() {
        return host;
    }

    /**
     * 设置
     * @param host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 获取
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * 设置
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * 获取
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return virtualHost
     */
    public String getVirtualHost() {
        return virtualHost;
    }

    /**
     * 设置
     * @param virtualHost
     */
    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String toString() {
        return "RabbitMQProperties{host = " + host + ", port = " + port + ", username = " + username + ", password = " + password + ", virtualHost = " + virtualHost + "}";
    }
}
