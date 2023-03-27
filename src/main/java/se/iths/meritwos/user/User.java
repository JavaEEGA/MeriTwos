package se.iths.meritwos.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.Set;

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



    private Set<Role> role;

    public User() {
    }

    public User(Long id, String name, String password, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role.add(role);
    }

    public User(Long id, String name, String password, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role.add(Role.valueOf(role));
    }

    public Role convertRole(String role) {

        switch (role.toUpperCase()) {
            case "ADMIN" -> {
                return Role.ROLE_ADMIN;
            }
            case "STUDENT" -> {
                return Role.ROLE_STUDENT;
            }
            case "COMPANY" -> {
                return Role.ROLE_COMPANY;
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

    public enum Role implements GrantedAuthority {
        ROLE_ADMIN, ROLE_STUDENT, ROLE_COMPANY;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}
