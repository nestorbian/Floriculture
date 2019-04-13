package com.nestor.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>处理跨域问题 同源策略[same origin policy]是浏览器的一个安全功能，不同源的客户端脚本在没有明确授权的情况下，不能读写对方资源。</p>
 * <p>同源策略是浏览器安全的基石。 什么是源，源[origin]就是协议、域名和端口号。若地址里面的协议、域名和端口号均相同则属于同源</p>
 * <p>为了解决浏览器同源问题，W3C 提出了跨源资源共享，即 CORS(Cross-Origin Resource Sharing)。</p>
 * 
 * @CrossOrigin(origins = {"http://localhost:9000", "null"})也可以通过注解
 * @author bianzeyang
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH").maxAge(3600).allowedHeaders("*");
    }

}
