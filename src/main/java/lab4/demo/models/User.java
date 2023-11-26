package lab4.demo.models;

import jakarta.persistence.*;
import lab4.demo.services.PasswordManager;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO normal authorization
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false, unique = true)
    private String password;  // hash

    public User(String login, String password) {
        id = 0L;
        this.login = login;
//        this.password = password;
        this.password = PasswordManager.getHash(password);
    }
}
