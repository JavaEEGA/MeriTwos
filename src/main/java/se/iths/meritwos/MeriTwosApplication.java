package se.iths.meritwos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.iths.meritwos.user.User;
import se.iths.meritwos.user.UserRepository;

@SpringBootApplication
public class MeriTwosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeriTwosApplication.class, args);
    }


    @Bean
    CommandLineRunner runOnStartUp(UserRepository repo, PasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                var user = repo.findByName("admin");
                if (user.isEmpty()) {
                    var adminUser = new User();
                    adminUser.setName("admin");
                    adminUser.setPassword(passwordEncoder.encode("admin"));
                    adminUser.getRole().add(User.Role.ADMIN);
                    repo.save(adminUser);
                    System.out.println("Admin added");
                }
            }
        };
    }
}

