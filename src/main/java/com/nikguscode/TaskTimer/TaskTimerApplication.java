package com.nikguscode.TaskTimer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages = "com.nikguscode.TaskTimer.model.entity")
@EnableJpaRepositories(basePackages = "com.nikguscode.TaskTimer.model.repository")
public class TaskTimerApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskTimerApplication.class, args);
	}
}
