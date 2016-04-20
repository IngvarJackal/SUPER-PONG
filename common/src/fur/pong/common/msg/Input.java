package fur.pong.common.msg;

import java.io.Serializable;

public class Input implements Serializable {
    long serialVersionUID = 0L;
    public final int ms;

    /* SPECIAL KEYCODES
        0x0 -- start game
        0x1 -- end game
     */
    public final char key;

    public Input(int ms, char key) {
        this.ms = ms;
        this.key = key;
    }
}
