package se.iths.meritwos.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.meritwos.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    private Mapper mapper;

    public UserController(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @GetMapping
    List<UserDTO> getAllUsers() {
        return mapper.mapUserToDTO(userRepository.findAll());
    }

    @GetMapping("/{id}")
    Optional<UserDTO> getUserByID(@PathVariable long id) {
        return mapper.mapUserToDTO(userRepository.findById(id).get());

    }
}
