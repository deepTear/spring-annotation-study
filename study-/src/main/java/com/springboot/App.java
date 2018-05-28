package com.springboot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.springboot.entity.Tiger;

@SpringBootApplication(scanBasePackages="com.springboot")
public class App {

	public static void main(String[] args) throws IOException {
		
		Properties properties = new Properties();
		InputStream in = App.class.getClassLoader().getResourceAsStream("config/spring-boot-application.properties");
		properties.load(in);
		SpringApplication app = new SpringApplication(App.class);
		app.setDefaultProperties(properties);
		//app.run(args);
		
		ConfigurableApplicationContext context = app.run(args);
		
		
		
		Tiger tiger = context.getBean(Tiger.class);
		System.out.println(tiger);
		
		context.close();
	}
}
