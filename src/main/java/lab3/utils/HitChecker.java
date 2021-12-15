package lab3.utils;

import static java.lang.Math.pow;

public class HitChecker {
    public static boolean checkArea(float x, float y, float R) {
        return (x >= 0 && x <= R && y <= 0 && y <= R) ||
                (x >= 0 && y >= 0 && pow(x,2) + pow(y,2) <= pow(R,2)) ||
                (x <= 0 && y >= 0 && y <= x + R/2);
    }
}
