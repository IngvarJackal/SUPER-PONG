package fur.pong.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import fur.pong.entities.Ball;
import fur.pong.entities.GameState;
import fur.pong.entities.Player;

import java.util.Arrays;

public class GameScene implements Scene {

    private static final int MOV_SPEED = 10;

    private final SpriteBatch batch;
    private final int curPlayer;

    private final int xMax = Gdx.graphics.getWidth();
    private final int yMax = Gdx.graphics.getHeight();
    GameState gameState = new GameState(Arrays.asList(new Player(Color.BLUE, 10, 10, yMax-10, yMax/2, "Blue player"),
                                        new Player(Color.RED, xMax-20, 10, yMax-10, yMax/2, "Red player")),
                                        Arrays.asList(new Ball(new Vector2(xMax/2, yMax/2), new Vector2(10, 5))));

    private final OrthographicCamera camera = new OrthographicCamera(xMax, yMax);
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private float cameraShift = 0f;

    public GameScene(SpriteBatch batch, int curPlayer) {
        this.batch = batch;
        this.curPlayer = curPlayer;
    }

    @Override
    public Scene workAndGetScene() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (gameState.players.get(curPlayer).addyPos(8))
                cameraShift -= 0.5;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (gameState.players.get(curPlayer).addyPos(-8));
                cameraShift += 0.5;
        } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            return new ExitScene();
        }
        return this;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Player player : gameState.players) {
            shapeRenderer.setColor(player.color);
            shapeRenderer.rect(player.xPos, player.getyPos()+cameraShift, player.HEIGHT, player.width);
        }
        for (Ball ball: gameState.balls) {
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(ball.position.x, ball.position.y+cameraShift, ball.RADIUS);
        }
        shapeRenderer.end();

    }
}
