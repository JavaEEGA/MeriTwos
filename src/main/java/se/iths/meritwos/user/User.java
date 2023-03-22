package se.iths.meritwos.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String name;
    @NotBlank
    private String password;
    private Role role;

    public User() {
    }

    public User(Long id, String name, String password, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User(Long id, String name, String password, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = convertRole(role);
    }

    private Role convertRole(String role) {

        switch (role.toUpperCase()) {
            case "ADMIN" -> {
                return Role.ADMIN;
            }
            case "STUDENT" -> {
                return Role.STUDENT;
            }
            case "COMPANY" -> {
                return Role.COMPANY;
            }
            default -> {
                return null;
            }

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 1;
    }

public enum Role {
    ADMIN, STUDENT, COMPANY
}
}
