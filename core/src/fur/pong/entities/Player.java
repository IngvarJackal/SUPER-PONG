package fur.pong.entities;

import com.badlogic.gdx.graphics.Color;

public class Player {
    public int width = 60; // px
    public final int HEIGHT = 8; // px

    public final Color color;
    public final int xPos;
    public final int yMin;
    public final int yMax;
    public final String name;
    private int yPos;

    public Player(Color color, int xPos, int yMin, int yMax, int yPos, String name) {
        this.color = color;
        this.xPos = xPos;
        this.yMin = yMin;
        this.yMax = yMax;
        this.yPos = yPos;
        this.name = name;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = Math.max(Math.min(yPos, yMax - width), yMin);
    }

    public boolean addyPos(int delta) {
        int prevPos = getyPos();
        setyPos(getyPos() + delta);
        return (prevPos != getyPos());
    }
}
