package fur.pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fur.pong.scenes.MenuScene;
import fur.pong.scenes.Scene;
import fur.pong.utils.KeyListener;

import static fur.pong.SharedState.keyListener;

public class SuperPongMain extends ApplicationAdapter {
    Scene curScene;

	@Override
	public void create () {
        curScene = new MenuScene(new SpriteBatch());
        keyListener = new KeyListener();
        Gdx.input.setInputProcessor(keyListener);
    }

	@Override
	public void render () {
        curScene = curScene.workAndGetScene();
        curScene.render();
    }
}
