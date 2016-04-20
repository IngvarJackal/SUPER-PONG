package fur.pong.common.msg;

import java.io.Serializable;

public class State implements Serializable {
    long serialVersionUID = 0L;
    public final int ms;
    public final int plApos;
    public final int plBpos;
    /* SPECIAL STATES
        0 -- waiting for game
        1 -- playing game
        2 -- game over; first won
        3 -- game over; second won
        4 -- first gamer resigned
        5 -- second gamer resigned
        6 -- draw
        7 -- time out
     */
    public final byte state;
    public final Ball[] balls;

    public State(int ms, int plApos, int plBpos, byte state, Ball[] balls) {
        this.ms = ms;
        this.plApos = plApos;
        this.plBpos = plBpos;
        this.state = state;
        this.balls = balls;
    }
}
