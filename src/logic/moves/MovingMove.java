package logic.moves;

import logic.Board;
import logic.Node;
import logic.NodeType;
import logic.controller.GameController;
import sample.Controller;

/**
 * Created by Cinek on 24.04.2019.
 */
public class MovingMove extends ChangePieceLocationMove {


    @Override
    public boolean isLegal(GameController controller, NodeType playerTurn) {

        return controller.wasNodeHereInPreviousMove(playerTurn, getToNodeIndex()) &&
                controller.getBoard().getNode(getToNodeIndex()-1).getNodeType() == NodeType.NONE &&
                controller.getBoard().getNode(getFromNodeIndex()-1).getNodeType() == playerTurn;
    }

    public MovingMove(int fromNodeIndex, int toNodeIndex, NodeType nodeType) {
        super(fromNodeIndex, toNodeIndex, nodeType );
    }


}
