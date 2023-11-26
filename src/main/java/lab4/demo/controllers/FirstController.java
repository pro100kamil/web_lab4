package lab4.demo.controllers;

import lab4.demo.dto.UserDto;
import lab4.demo.models.User;
import lab4.demo.dao.UserRepository;
import lab4.demo.services.AuthenticationManager;
import lab4.demo.services.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class FirstController {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @CrossOrigin
    @PostMapping("/check")
    public User checkUser(@RequestBody UserDto userDto) {
        String login = userDto.getLogin();
        String password = userDto.getPassword();

        return authenticationManager.getOldUser(login, password);
    }

    @CrossOrigin
    @PostMapping("/new")
    public User getNewUser(@RequestBody UserDto userDto) {
        String login = userDto.getLogin();
        String password = userDto.getPassword();

        User user = authenticationManager.getNewUser(login, password);

        userRepository.save(user);
        return user;
    }
}
