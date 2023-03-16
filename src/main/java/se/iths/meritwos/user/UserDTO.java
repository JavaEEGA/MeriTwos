package se.iths.meritwos.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class UserDTO {

    private Long id;
    private String name;
    private String password;
    private User.Role role;

}
