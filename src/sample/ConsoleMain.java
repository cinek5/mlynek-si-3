package sample;

import logic.NodeType;
import logic.ai.MinMaxComputerAI;
import logic.controller.*;
import logic.evaluation.FirstGameStateEvaluator;

/**
 * Created by Cinek on 24.04.2019.
 */
public class ConsoleMain {

    public static void main(String[] args)
    {
        GameInput whiteInput = new PlayerInput(NodeType.WHITE);
        //GameInput blackInput = new PlayerInput(NodeType.BLACK);
        GameInput blackInput = new ComputerPlayerInput(NodeType.BLACK, new MinMaxComputerAI(new FirstGameStateEvaluator(), NodeType.BLACK ));
        GameController gameController = new GameController(NodeType.WHITE, whiteInput, blackInput);
        gameController.game();
    }
}
