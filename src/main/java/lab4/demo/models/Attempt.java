package lab4.demo.models;

import lombok.Data;

@Data
public class Attempt {
    private double x;

    private double y;

    private int r;

    private boolean isHit;

    public Attempt(String strX, String strY, String strR) {
        x = Double.parseDouble(strX);
        y = Double.parseDouble(strY);
        r = Integer.parseInt(strR);

        isHit = Checker.checkHit(this.x, this.y, this.r);
    }
}
