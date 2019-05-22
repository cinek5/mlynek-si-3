package sample;

import logic.NodeType;
import logic.ai.AlphaBetaComputerAI;
import logic.ai.MinMaxComputerAI;
import logic.controller.*;
import logic.evaluation.FirstGameStateEvaluator;
import logic.evaluation.SecondGameStateEvaluator;

/**
 * Created by Cinek on 24.04.2019.
 */
public class ConsoleMain {

    public static void main(String[] args)
    {
//        GameInput whiteInput = new ComputerPlayerInput(NodeType.WHITE, new AlphaBetaComputerAI(new SecondGameStateEvaluator(null), NodeType.WHITE , 5));
//        //GameInput whiteInput = new PlayerInput(NodeType.WHITE);
//        //GameInput blackInput = new PlayerInput(NodeType.BLACK);
//        GameInput blackInput = new ComputerPlayerInput(NodeType.BLACK, new AlphaBetaComputerAI(new SecondGameStateEvaluator(null), NodeType.BLACK , 5));
//        GameController gameController = new GameController(NodeType.BLACK, whiteInput, blackInput);
//        gameController.game();
    }
}
