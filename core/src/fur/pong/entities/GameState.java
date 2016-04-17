package fur.pong.entities;

import java.util.List;

public class GameState {
    public final List<Player> players;
    public final List<Ball> balls;

    public GameState(List<Player> players, List<Ball> balls) {
        this.players = players;
        this.balls = balls;
    }
}
