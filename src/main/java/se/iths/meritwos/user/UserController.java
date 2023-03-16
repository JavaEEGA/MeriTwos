package se.iths.meritwos.user;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
        return mapper.mapUserToDTO(userRepository.findById(id).orElseThrow());
    }

    @PostMapping
    void addUser(@Validated @RequestBody User user) {
        if (user.getRole() == User.Role.Student || user.getRole() == User.Role.Company || user.getRole() == User.Role.Admin)
            userRepository.save(user);
        else throw new IllegalArgumentException();

    }
}
