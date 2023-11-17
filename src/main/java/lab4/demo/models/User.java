package lab4.demo.models;

import lombok.Data;

@Data
public class User {
    private int id;
    private String login;
    private String password;

    public User(String login, String password) {
        id = 0;
        this.login = login;
        this.password = password;
    }
}
