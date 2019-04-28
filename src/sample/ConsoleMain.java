package sample;

import logic.NodeType;
import logic.controller.*;

/**
 * Created by Cinek on 24.04.2019.
 */
public class ConsoleMain {

    public static void main(String[] args)
    {
        GameInput whiteInput = new PlayerInput(NodeType.WHITE);
        GameInput blackInput = new PlayerInput(NodeType.BLACK);
        GameController gameController = new GameController(PlayerTurn.WHITE, whiteInput, blackInput);
        gameController.game();
    }
}
