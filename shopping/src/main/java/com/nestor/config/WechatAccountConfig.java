package com.nestor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    private String appId;
    private String appSecret;
    private String mchId;
    private String mchKey;
    private String keyPath;
    private String notifyUrl;

}
