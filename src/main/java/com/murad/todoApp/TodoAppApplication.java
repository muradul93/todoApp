package com.murad.todoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.murad.todoApp.config",
		"com.murad.todoApp.controllers",
		"com.murad.todoApp.services",
		"com.murad.todoApp.repositories",
		"com.murad.todoApp.dao"})
public class TodoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}

}
