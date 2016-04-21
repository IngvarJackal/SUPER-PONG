package fur.pong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fur.pong.SuperPongMain;

import static fur.pong.common.constants.Constants.HEIGHT;
import static fur.pong.common.constants.Constants.WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = WIDTH;
        config.width = HEIGHT;
		new LwjglApplication(new SuperPongMain(), config);
	}
}
