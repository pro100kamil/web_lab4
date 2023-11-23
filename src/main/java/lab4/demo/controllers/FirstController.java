package lab4.demo.controllers;

import lab4.demo.dto.UserDto;
import lab4.demo.models.User;
import lab4.demo.dao.UserRepository;
import lab4.demo.services.PasswordManager;
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
    @PostMapping("/check")
//    public User check_user(@RequestParam(value = "login") String login,
//                          @RequestParam(value = "password") String password) {
    //RequestBody вместо RequestParam, потому RequestParam работает только с адресной строкой
    public User check_user(@RequestBody UserDto userDto) {
        String hashPassword = PasswordManager.getHash(userDto.getPassword());
        for (User user : userRepository.findAll()) {
            if (user.getLogin().equals(userDto.getLogin()) && user.getPassword().equals(hashPassword)) {
                return user;
            }
        }
        return null;
    }

    @CrossOrigin
    @PostMapping("/new")
    public User get_new_user(@RequestBody UserDto userDto) {
        String login = userDto.getLogin();
        String password = userDto.getPassword();
        for (User user : userRepository.findAll()) {
            //пользователь с таким логином уже существует
            if (user.getLogin().equals(login)) {
                return null;
            }
        }
        User user = new User(login, password);
        userRepository.save(user);
        return user;
    }
}
