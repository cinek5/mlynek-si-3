package logic.moves;

import logic.Board;
import logic.NodeType;
import logic.controller.GameController;

/**
 * Created by Cinek on 24.04.2019.
 */
public interface Move {

    boolean isLegal(GameController controller, NodeType playerTurn);
    void makeMove(Board board);
    void undoMove(Board board);

}

