package fur.pong.common.msg;

import java.io.Serializable;

public class Ball implements Serializable {
    long serialVersionUID = 0L;
    public final int x;
    public final int y;
    public final int xVel;
    public final int yVel;

    public Ball(int x, int y, int xVel, int yVel) {
        this.x = x;
        this.y = y;
        this.xVel = xVel;
        this.yVel = yVel;
    }
}
