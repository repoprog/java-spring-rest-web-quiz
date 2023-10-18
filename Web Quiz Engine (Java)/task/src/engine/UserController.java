package engine;

import engine.UserRepository;
import engine.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class UserController {

    @Autowired
    private final UserService userService;
    //Constructor because final variables has to be initialized

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    public void createUser(@Valid @RequestBody User user) {
        userService.createUser(user);
    }
}