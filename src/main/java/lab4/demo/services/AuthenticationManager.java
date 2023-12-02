package lab4.demo.services;

import lab4.demo.dao.RoleRepository;
import lab4.demo.dao.UserRepository;
import lab4.demo.models.Role;
import lab4.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationManager {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public User getNewUser(String login, String password) {
        for (User user : userRepository.findAll()) {
            //пользователь с таким логином уже существует
            if (user.getLogin().equals(login)) {
                return null;
            }
        }
        return new User(login, password, roleRepository.findByName("min_user"));
    }

    public User getOldUser(String login, String password) {
        if (login == null || password == null) return null;

        String hashPassword = PasswordManager.getHash(password);
        for (User user : userRepository.findAll()) {
            if (user.getLogin().equals(login) && user.getPassword().equals(hashPassword)) {
                return user;
            }
        }
        return null;
    }

    public User getOldUserByHash(String login, String hashPassword) {
        if (login == null || hashPassword == null) return null;

        for (User user : userRepository.findAll()) {
            if (user.getLogin().equals(login) && user.getPassword().equals(hashPassword)) {
                return user;
            }
        }
        return null;
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
