package com.nestor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.WxPayServiceImpl;

@Configuration
public class WechatPayConfig {
    
    @Autowired
    private WechatAccountConfig wechatAccountConfig;
    
    @Bean(name = "wxPayH5Config")
    public WxPayH5Config wxPayH5Config() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(wechatAccountConfig.getAppId());
        wxPayH5Config.setAppSecret(wechatAccountConfig.getAppSecret());
        wxPayH5Config.setMchId(wechatAccountConfig.getMchId());
        wxPayH5Config.setMchKey(wechatAccountConfig.getMchKey());
        wxPayH5Config.setKeyPath(wechatAccountConfig.getKeyPath());
        wxPayH5Config.setNotifyUrl(wechatAccountConfig.getNotifyUrl());
        return wxPayH5Config;
    }
    
    @Bean(name = "wxPayService")
    public WxPayServiceImpl wxPayService(WxPayH5Config wxPayH5Config) {
        WxPayServiceImpl wxPayServiceImpl = new WxPayServiceImpl();
        wxPayServiceImpl.setWxPayH5Config(wxPayH5Config);
        return wxPayServiceImpl;
    }
}
