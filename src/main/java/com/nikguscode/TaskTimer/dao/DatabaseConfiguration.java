package com.nikguscode.TaskTimer.dao;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.nikguscode.TaskTimer")
@PropertySource("classpath:application.properties")
public class DatabaseConfiguration {
}
