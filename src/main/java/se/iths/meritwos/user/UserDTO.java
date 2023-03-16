package se.iths.meritwos.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
public class UserDTO {

    private Long id;
    private String name;
    private String password;
    private User.Role role;


    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}
