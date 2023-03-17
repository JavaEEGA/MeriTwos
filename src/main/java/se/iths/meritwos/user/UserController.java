package se.iths.meritwos.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import se.iths.meritwos.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    private final Mapper mapper;

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
    void addUser(@Valid @RequestBody User user) {
        if (validateRole(user))
            userRepository.save(user);
        else throw new IllegalArgumentException();

    }

    @PutMapping("/{id}")
    void updateUserById(@PathVariable long id, @Valid @RequestBody User user) {
        if (validateRole(user)) {
            userRepository.deleteById(id);
            userRepository.save(user);
        } else throw new IllegalArgumentException();

    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);

    }


    private static boolean validateRole(User user) {
        return user.getRole() == User.Role.Student || user.getRole() == User.Role.Company || user.getRole() == User.Role.Admin;
    }
}
