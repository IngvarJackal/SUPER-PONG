package fur.pong.utils;

import com.badlogic.gdx.Gdx;

public class SparseEvent {

    private double cursecond = 0;
    private double maxseconds;
    private Runnable action;

    public SparseEvent(double maxseconds, Runnable action) {
        this.maxseconds = maxseconds;
        this.action = action;
    }

    public void run() {
        cursecond += Gdx.graphics.getDeltaTime();
        if (cursecond >= maxseconds) {
            cursecond = 0;
            action.run();
        }
    }

}
