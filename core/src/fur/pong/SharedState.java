package fur.pong;

import fur.pong.utils.KeyListener;

public class SharedState {
    public volatile static boolean settingsSet = false;
    public volatile static String ip = "";
    public volatile static String port = "";
    public volatile static KeyListener keyListener = null;
}
