package se.iths.meritwos.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;


@Getter
@Setter
@Document(collection = "users")
public class User {

    @NotBlank
    private String name;
    @NotBlank
    private String password;
    private Role role;

    public User() {
    }

    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public enum Role {
        Admin, Student, Company
    }
}
