package fur.pong.server.collections;

import fur.pong.common.msg.Input;

public class InputTuple {
    private Input x;
    private Input y;

    public Input getX() {
        return x;
    }
    public void setX(Input x) {
        this.x = x;
    }
    public Input getY() {
        return y;
    }
    public void setY(Input y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "InputTuple{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}