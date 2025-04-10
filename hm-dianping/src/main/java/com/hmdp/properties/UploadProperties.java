package com.hmdp.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.upload")
public class UploadProperties {
    private String url;

    public UploadProperties() {
    }

    public UploadProperties(String url) {
        this.url = url;
    }

    /**
     * 获取
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return "UploadProperties{url = " + url + "}";
    }
}
