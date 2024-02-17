package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void createUser(@RequestBody User user) {
        Optional<User> optionalUser = userRepository
                .findByEmail(user.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserExistsException("Uzytkownik jest już w bazie danych: "
                    + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()){
            return user.get();
        }else{
            throw new UsernameNotFoundException(String.format("Username[%username] not found"));
        }
    }
}