package fur.pong.entities;

import com.badlogic.gdx.math.Vector2;

public class Ball {
    public final static int RADIUS = 6; // px

    public Vector2 position;
    public Vector2 velocity;

    public Ball(Vector2 position, Vector2 velocity) {
        this.position = position;
        this.velocity = velocity;
    }
}
