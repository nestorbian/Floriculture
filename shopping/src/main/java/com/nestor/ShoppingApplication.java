package com.nestor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@EnableConfigurationProperties
@EnableScheduling
@EnableAspectJAutoProxy
public class ShoppingApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}
}
