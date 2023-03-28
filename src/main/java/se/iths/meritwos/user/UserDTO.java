package se.iths.meritwos.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class UserDTO {

    private Long id;
    private String name;
    private String password;
    private String role;


    public UserDTO(User user) {
        this.name = user.getName();
        this.password = user.getPassword();
        this.role = user.getRole().toString();
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
