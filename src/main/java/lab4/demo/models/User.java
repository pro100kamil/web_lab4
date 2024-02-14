package lab4.demo.models;

import jakarta.persistence.*;
import lab4.demo.services.PasswordManager;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private String password;  // hash

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = PasswordManager.getHash(password);
        this.role = role;
    }
}
