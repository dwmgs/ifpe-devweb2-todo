package com.devweb2.project.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TasksApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TasksApplication.class);
        app.setAdditionalProfiles("postgresql");
        app.run(args);
	}

}
