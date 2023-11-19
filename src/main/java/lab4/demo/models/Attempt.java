package lab4.demo.models;

import jakarta.persistence.*;
import lab4.demo.utilities.Checker;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "attempts")
public class Attempt {
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

    public Attempt(String strX, String strY, String strR) {
        x = Double.parseDouble(strX);
        y = Double.parseDouble(strY);
        r = Integer.parseInt(strR);

        isHit = Checker.checkHit(this.x, this.y, this.r);
    }
}
