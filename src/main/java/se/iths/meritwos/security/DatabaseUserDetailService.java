package se.iths.meritwos.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.meritwos.user.User;
import se.iths.meritwos.user.UserRepository;

@Service
public class DatabaseUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    private DatabaseUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByName(userName).orElseThrow();


        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), user.getRole());
    }
}
