package com.devweb2.project.notifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotifierApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(NotifierApplication.class);
        app.setAdditionalProfiles("notifier");
        app.run(args);
	}

}
