package lab4.demo.models;

public class Checker {
    public static boolean checkHit(double x, double y, int r) {
        if (x <= 0 && y >= 0 && x * x + y * y <= r * r) {  //вторая четверть
            return true;
        }
        if (x <= 0 && y <= 0 && y >= -x - r) {  //третья четверть
            return true;
        }
        if (x >= 0 && y <= 0 && x <= r && y >= (double) -r / 2) {  //четвёртая четверть
            return true;
        }
        return false;
    }
}