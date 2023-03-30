package se.iths.meritwos.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.iths.meritwos.company.Company;
import se.iths.meritwos.mapper.Mapper;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

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

    @GetMapping("/{name}")
    Optional<UserDTO> getUserByID(@PathVariable String name) {
        return mapper.mapUserToDTO(userRepository.findByName(name).orElseThrow());
    }

    @Secured("ADMIN")
    @PostMapping("/register")
    ResponseEntity<Void> addUser(@Valid @RequestBody User user) {

        if (userRepository.findByName(user.getName()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        if (validateRole(user)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } else throw new IllegalArgumentException();

    }

    @PostMapping (value = "/newuser", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Void> addUserByForm(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String redirectURI;
        if (user.getRole().contains(User.Role.STUDENT)){
            redirectURI = "/newstudent";
        }
        else { redirectURI ="/newcompany";}

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectURI))
                .build();
    }

    @PutMapping("/{name}")
    void updateUserByName(@PathVariable String name, @Valid @RequestBody User user) {
        if (validateRole(user)) {
            var userToUpdate = userRepository.findByName(name).orElseThrow();
            userToUpdate.setName(user.getName());
            userToUpdate.setRole(user.getRole());
            userToUpdate.setPassword(user.getPassword());
            userRepository.save(userToUpdate);
        } else throw new IllegalArgumentException();

    }

    @DeleteMapping("/{name}")
    void deleteUser(@PathVariable String name) {
        userRepository.deleteByName(name);

    }


    private static boolean validateRole(User user) {
        return user.getRole().contains(User.Role.STUDENT) || user.getRole().contains(User.Role.COMPANY) || user.getRole().contains(User.Role.ADMIN);
    }
}
