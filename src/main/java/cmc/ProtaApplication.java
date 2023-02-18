package cmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ProtaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProtaApplication.class, args);
	}

}
