package logic.ai;

import logic.NodeType;
import logic.controller.GameController;
import logic.moves.Move;

/**
 * Created by Cinek on 07.05.2019.
 */
public interface ComputerAI
{
    Move getBestPossibleMove(GameController gameController);
}
