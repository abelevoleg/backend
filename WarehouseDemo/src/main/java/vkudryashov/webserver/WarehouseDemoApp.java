package vkudryashov.webserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class WarehouseDemoApp {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseDemoApp.class, args);
	}

}
