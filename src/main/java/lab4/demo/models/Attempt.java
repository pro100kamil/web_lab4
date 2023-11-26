package lab4.demo.models;

import jakarta.persistence.*;
import lab4.demo.services.Checker;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "attempts")
public class Attempt {
    //TODO сделать чтобы попытка хранила юзера который сделал эту попытку
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double x;

    @Column(nullable = false)
    private double y;

    @Column(nullable = false)
    private int r;

    @Column(nullable = false)
    private boolean isHit;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Attempt(String strX, String strY, String strR, User user) {
        x = Double.parseDouble(strX);
        y = Double.parseDouble(strY);
        r = Integer.parseInt(strR);
        this.user = user;

        isHit = Checker.checkHit(this.x, this.y, this.r);
    }
}
