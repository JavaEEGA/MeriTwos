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
            var userToUpdate = userRepository.findById(id).orElseThrow();
            userToUpdate.setName(user.getName());
            userToUpdate.setRole(user.getRole());
            userToUpdate.setPassword(user.getPassword());
            userRepository.save(userToUpdate);
        } else throw new IllegalArgumentException();

    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);

    }


    private static boolean validateRole(User user) {
        return user.getRole() == User.Role.STUDENT || user.getRole() == User.Role.COMPANY || user.getRole() == User.Role.ADMIN;
    }
}
