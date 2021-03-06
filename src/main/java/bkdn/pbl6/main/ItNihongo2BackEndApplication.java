package bkdn.pbl6.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ItNihongo2BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItNihongo2BackEndApplication.class, args);
	}

}
