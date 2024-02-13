package io.picky.panda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PickyPandaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PickyPandaApplication.class, args);
	}

}
