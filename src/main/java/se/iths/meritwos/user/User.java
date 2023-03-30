package se.iths.meritwos.user;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@Document(collection = "users")
public class User {
    @Id
    String id;
    @NotBlank
    @Column(unique = true)
    private String name;
    @NotBlank
    private String password;
    private Set<Role> role = new HashSet<>();

    public User() {
    }

    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role.add(role);
    }

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role.add(Role.valueOf(role));
    }

    public Role convertRole(String role) {

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
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public enum Role implements GrantedAuthority {
        ADMIN, STUDENT, COMPANY;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}
