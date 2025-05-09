package ru.otus.hw;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class Application {

	/**
	 * <a href="http://localhost:8080/">...</a>
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
