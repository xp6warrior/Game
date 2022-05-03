import Entities.Player;
import UI.GameWindow;

public class Init {
    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();

        new Player(gameWindow, 5, 1);
    }
}