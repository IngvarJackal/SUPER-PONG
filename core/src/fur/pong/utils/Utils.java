package fur.pong.utils;

public class Utils {
    public static float revertY(double y, double yMax) {
        return (float) (yMax - y);
    }

    public static int revertY(int y, int yMax) {
        return  yMax - y;
    }
}
