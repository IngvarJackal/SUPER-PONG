package fur.pong.scenes;

public class ExitScene implements Scene {
    @Override
    public Scene workAndGetScene() {
        System.exit(0);
        return null;
    }

    @Override
    public void render() {}
}
