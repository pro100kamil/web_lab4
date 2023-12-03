package lab4.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttemptDto {
    private double x;

    private double y;

    private int r;

    private boolean isHit;
}
