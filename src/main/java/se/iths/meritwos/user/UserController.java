package se.iths.meritwos.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.iths.meritwos.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    //TODO REDO METHODS TO HANDLE USERS
    //TODO REDO controller to use MongoDB to Store Users

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Mapper mapper;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, Mapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @GetMapping
    List<UserDTO> getAllUsers() {
        return mapper.mapUserToDTO(userRepository.findAll());
    }

    @GetMapping("/{id}")
    Optional<UserDTO> getUserByID(@PathVariable String id) {
        return mapper.mapUserToDTO(userRepository.findById(id).orElseThrow());
    }

    @Secured("ADMIN")
    @PostMapping("/register")
    ResponseEntity<Void> addUser(@RequestBody User user) {

        if (userRepository.findByName(user.getName()) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        if (validateRole(user)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } else throw new IllegalArgumentException();

    }

    @PutMapping("/{id}")
    void updateUserById(@PathVariable String id, @RequestBody User user) {
        if (validateRole(user)) {
            var userToUpdate = userRepository.findById(id).orElseThrow();
            userToUpdate.setName(user.getName());
            userToUpdate.setRole(user.getRole());
            userToUpdate.setPassword(user.getPassword());
            userRepository.save(userToUpdate);
        } else throw new IllegalArgumentException();

    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);

    }


    private static boolean validateRole(User user) {
        return user.getRole().contains(User.Role.STUDENT) || user.getRole().contains(User.Role.COMPANY) || user.getRole().contains(User.Role.ADMIN);
    }
}
