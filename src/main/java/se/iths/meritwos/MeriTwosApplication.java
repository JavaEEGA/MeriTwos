package se.iths.meritwos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class MeriTwosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeriTwosApplication.class, args);
    }
}
