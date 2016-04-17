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

import java.util.Arrays;
import java.util.List;

import static fur.pong.utils.Utils.revertY;


public class MenuScene implements Scene {
    private SpriteBatch batch;

    private final BitmapFont whiteFont = new BitmapFont(); {whiteFont.setColor(Color.WHITE);}
    private final BitmapFont blackFont = new BitmapFont(); {blackFont.setColor(Color.BLACK);}

    private final List<GlyphLayout> whiteMenuItems = Arrays.asList(new GlyphLayout(whiteFont, "Start game"), new GlyphLayout(whiteFont, "Exit"), new GlyphLayout(whiteFont, "Exit2"));
    private final List<GlyphLayout> blackMenuItems = Arrays.asList(new GlyphLayout(blackFont, "Start game"), new GlyphLayout(blackFont, "Exit"), new GlyphLayout(blackFont, "Exit2"));

    private final List<Runnable> menuActions = Arrays.asList(() -> reutrnedScene = new GameScene(batch, 0),
                                                             () -> reutrnedScene = new ExitScene(),
                                                             () -> reutrnedScene = new ExitScene());
    private final int menuLength = whiteMenuItems.size()-1;

    private final int xCenter = Gdx.graphics.getWidth()/2;
    private final float yPad = 0.4f; // % of the screen
    private final float yMax = Gdx.graphics.getHeight();
    private final int yStart = (int) (yMax*yPad);
    private final int ySpacing = (int) (yMax*(1-yPad*2)/menuLength);
    private final int textSelectionPad = 3; // px

    private int selectedPosition = 0;
    private Scene reutrnedScene = this;

    private final OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public MenuScene(SpriteBatch batch) {
        this.batch = batch;
    };

    @Override
    public Scene workAndGetScene() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedPosition = Math.max(selectedPosition-1, 0);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedPosition = Math.min(selectedPosition+1, menuLength);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ||
                Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            menuActions.get(selectedPosition).run();
        }
        return reutrnedScene;
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
                shapeRenderer.rect(xCenter - menuItem.width / 2 - textSelectionPad,
                        revertY(yPos + menuItem.height - textSelectionPad, yMax),
                        menuItem.width + textSelectionPad * 2,
                        menuItem.height + textSelectionPad * 2);
                shapeRenderer.end();
                menuItem = blackMenuItems.get(i);
            }
            batch.begin();
            whiteFont.draw(batch, menuItem, xCenter - menuItem.width / 2, revertY(yPos - menuItem.height / 2, yMax));
            batch.end();
            yPos += ySpacing;
        }
    }


}
