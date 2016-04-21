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

    @Override
    public String toString() {
        return "Input{" +
                "serialVersionUID=" + serialVersionUID +
                ", ms=" + ms +
                ", key=" + key +
                '}';
    }
}
