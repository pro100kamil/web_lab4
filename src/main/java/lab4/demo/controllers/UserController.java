package lab4.demo.controllers;

import lab4.demo.dao.UserRepository;
import lab4.demo.dto.UserDto;
import lab4.demo.models.User;
import lab4.demo.services.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {
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
    public boolean checkUser(@RequestBody UserDto userDto) {
        String login = userDto.getLogin();
        String password = userDto.getPassword();

        User user = authenticationManager.getOldUser(login, password);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad request");
        }

        return true;
    }

    @CrossOrigin
    @PostMapping("/new")
    public boolean getNewUser(@RequestBody UserDto userDto) {
        String login = userDto.getLogin();
        String password = userDto.getPassword();

        User user = authenticationManager.getNewUser(login, password);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad request");
        }

        userRepository.save(user);

        return true;
    }
}
