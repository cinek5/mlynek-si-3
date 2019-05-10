package logic.controller;

import logic.NodeType;
import logic.ai.ComputerAI;
import logic.ai.MinMaxComputerAI;
import logic.evaluation.FirstGameStateEvaluator;
import logic.moves.Move;

/**
 * Created by Cinek on 24.04.2019.
 */
public class ComputerPlayerInput implements GameInput {

    private NodeType nodeType;
    private ComputerAI computerAI;

    public ComputerPlayerInput(NodeType nodeType, ComputerAI computerAI) {
        this.nodeType = nodeType;
        this.computerAI = computerAI;

    }

    public Move getMove(GameController controller) {
        return computerAI.getBestPossibleMove(controller);
    }
}
