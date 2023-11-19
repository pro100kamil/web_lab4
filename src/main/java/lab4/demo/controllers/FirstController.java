package lab4.demo.controllers;

import lab4.demo.models.User;
import lab4.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class FirstController {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CrossOrigin
    @GetMapping("/check")
    public User check_user(@RequestParam(value = "login") String login,
                          @RequestParam(value = "password") String password) {
        for (User user : userRepository.findAll()) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @CrossOrigin
    @PostMapping("/new")
    public User get_new_user(@RequestParam(value = "login", required = false, defaultValue = "def") String login,
                          @RequestParam(value = "password", required = false, defaultValue = "def") String password) {
        System.out.println(login + " : " + password);
        for (User user : userRepository.findAll()) {
            //пользователь с таким логином уже существует
            if (user.getLogin().equals(login)) {
                return null;
            }
        }
        User user = new User(login, password);
        userRepository.save(user);
        //TODO сделать хеширование
        return user;
    }
}
