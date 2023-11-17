package lab4.demo.controllers;

import lab4.demo.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class FirstController {
    @GetMapping("/new")
    public User check_hit(@RequestParam(value = "login", required = false, defaultValue = "-1") String login,
                          @RequestParam(value = "password", required = false, defaultValue = "-1") String password) {
        return new User(login, password);
    }
}
