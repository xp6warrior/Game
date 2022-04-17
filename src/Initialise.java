import Entities.Player;
import Handlers.InputHandler;
import UI.GameWindow;

public class Initialise {
    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();

        Player player = new Player(gameWindow, 5);
        new InputHandler(gameWindow, player);
    }
}