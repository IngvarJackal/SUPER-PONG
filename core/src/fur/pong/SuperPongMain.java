package fur.pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fur.pong.scenes.MenuScene;
import fur.pong.scenes.Scene;

public class SuperPongMain extends ApplicationAdapter {
    Scene curScene;

	@Override
	public void create () {
        curScene = new MenuScene(new SpriteBatch());
    }

	@Override
	public void render () {
        curScene = curScene.workAndGetScene();
        curScene.render();
    }
}
