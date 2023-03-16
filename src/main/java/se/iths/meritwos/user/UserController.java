package se.iths.meritwos.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.meritwos.mapper.Mapper;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    private Mapper mapper;

    public UserController(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


}
