package se.iths.meritwos.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Setter
@Getter
public class UserDTO {

    private Long id;
    private String name;
    private Set<se.iths.meritwos.user.User.Role> role;


    public UserDTO(User user) {
        ;
        this.name = user.getName();
        this.role = user.getRole();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
