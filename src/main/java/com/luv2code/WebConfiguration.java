package com.luv2code;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = "com.luv2code.entity")
@EnableTransactionManagement
@EnableAutoConfiguration
public class WebConfiguration {

	
}