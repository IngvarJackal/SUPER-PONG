package fur.pong.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fur.pong.SharedState;
import fur.pong.utils.SparseEvent;

import java.util.Arrays;
import java.util.List;

import static fur.pong.utils.Utils.revertY;

public class SettingsScene implements Scene {
    private SpriteBatch batch;
    private final Scene afterEscapeScene;

    private final BitmapFont whiteFont = new BitmapFont(); {whiteFont.setColor(Color.WHITE);}
    private final BitmapFont blackFont = new BitmapFont(); {blackFont.setColor(Color.BLACK);}

    private final List<GlyphLayout> whiteMenuItems = Arrays.asList(new GlyphLayout(whiteFont, "IP: "), new GlyphLayout(whiteFont, "Port: "));
    private final List<GlyphLayout> blackMenuItems = Arrays.asList(new GlyphLayout(blackFont, "IP: "), new GlyphLayout(blackFont, "Port: "));
    private final List<StringBuilder> menuFileds = Arrays.asList(new StringBuilder(), new StringBuilder());

    private final int menuLength = whiteMenuItems.size()-1;
    private int selectedPosition = 0;

    private final int xCenter = Gdx.graphics.getWidth()/2;
    private final float yPad = 0.45f; // % of the screen
    private final float yMax = Gdx.graphics.getHeight();
    private final int yStart = (int) (yMax*yPad);
    private final int ySpacing = (int) (yMax*(1-yPad*2)/menuLength);
    private final int textSelectionPad = 3; // px

    private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public SettingsScene(SpriteBatch batch, Scene afterEscapeScene) {
        this.batch = batch;
        this.afterEscapeScene = afterEscapeScene;
        menuFileds.get(0).append(SharedState.ip);
        menuFileds.get(1).append(SharedState.port);
    }

    private Runnable deleteAction = () -> {
        if (menuFileds.get(selectedPosition).length() > 0)
            menuFileds.get(selectedPosition).deleteCharAt(menuFileds.get(selectedPosition).length()-1);
    };
    private final SparseEvent deleteEvent = new SparseEvent(0.1, deleteAction);
    @Override
    public Scene workAndGetScene() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedPosition = Math.max(selectedPosition-1, 0);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedPosition = Math.min(selectedPosition+1, menuLength);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            return afterEscapeScene;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            SharedState.ip = menuFileds.get(0).toString();
            SharedState.port = menuFileds.get(1).toString();
            SharedState.settingsSet = true;
            return afterEscapeScene;
        } else if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE))
                deleteAction.run();
            else
                deleteEvent.run();
        } else {
            char c = SharedState.keyListener.getCharTyped();
            if (c != '\0')
                menuFileds.get(selectedPosition).append(c);
        }

        return this;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        int yPos = yStart;
        for (int i = 0; i <= menuLength; i++) {
            GlyphLayout menuItem = whiteMenuItems.get(i);
            if (i == selectedPosition) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.WHITE);
                shapeRenderer.rect(xCenter - 100 - textSelectionPad,
                        revertY(yPos + menuItem.height - textSelectionPad, yMax),
                        menuItem.width + textSelectionPad * 2,
                        menuItem.height + textSelectionPad * 2);
                shapeRenderer.end();
                menuItem = blackMenuItems.get(i);
            }
            batch.begin();
            whiteFont.draw(batch, menuItem, xCenter - 100, revertY(yPos - menuItem.height / 2, yMax));
            whiteFont.draw(batch, menuFileds.get(i).toString(), xCenter - 60, revertY(yPos - menuItem.height / 2, yMax));
            batch.end();
            yPos += ySpacing;
        }
    }
}
