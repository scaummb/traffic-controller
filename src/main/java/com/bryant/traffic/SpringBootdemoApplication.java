package com.bryant.traffic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.bryant.traffic.*")
public class SpringBootdemoApplication {
 
	public static void main(String[] args) {
	    SpringApplication.run(SpringBootdemoApplication.class, args);
	}

}
