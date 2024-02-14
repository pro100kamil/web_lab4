package lab4.demo.services;

public class AttemptValidator {
    //стоило сделать все статические сервисы как реализации интерфейсов в целях расширяемости, но ладно
    public static boolean validateX(String strX) {
        if (strX == null) return false;
        if (strX.length() > 7) return false;
        try {
            double x = Double.parseDouble(strX);
            return -5 <= x && x <= 5;
        }
        catch (NumberFormatException _ignored) {
            return false;
        }
    }
    public static boolean validateY(String strY) {
        if (strY == null) return false;
        if (strY.length() > 7) return false;
        try {
            double y = Double.parseDouble(strY);
            return -5 <= y && y <= 5;
        }
        catch (NumberFormatException _ignored) {
            return false;
        }
    }

    public static boolean validateR(String strR) {
        if (strR == null) return false;
        try {
            int r = Integer.parseInt(strR);
            return 1 <= r && r <= 5;
        }
        catch (NumberFormatException _ignored) {
            return false;
        }
    }

    public static boolean validateXYR(String strX, String strY, String strR) {
        return validateX(strX) && validateY(strY) && validateR(strR);
    }
}
