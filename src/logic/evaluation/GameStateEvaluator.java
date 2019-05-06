package logic.evaluation;

import logic.NodeType;
import logic.controller.GameController;

/**
 * Created by Cinek on 06.05.2019.
 */
public interface GameStateEvaluator {
    int evaluatePlacingPhase(GameController gameController, NodeType playerTurn);
    int evaluateSlidingPhase(GameController gameController, NodeType playerTurn);
    int evaluateMovingPhase(GameController gameController, NodeType playerTurn);
}
