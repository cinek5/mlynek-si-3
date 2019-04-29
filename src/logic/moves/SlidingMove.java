package logic.moves;

import logic.NodeType;
import logic.controller.GameController;

/**
 * Created by Cinek on 29.04.2019.
 */
public class SlidingMove extends ChangePieceLocationMove{


    public SlidingMove(int fromNodeIndex, int toNodeIndex, NodeType nodeType) {
        super(fromNodeIndex, toNodeIndex, nodeType);
    }

    @Override
    public boolean isLegal(GameController controller, NodeType playerTurn) {
        return controller.wasNodeHereInPreviousMove(playerTurn, getToNodeIndex()) &&
                controller.getBoard().getNode(getToNodeIndex() - 1).getNodeType() == NodeType.NONE &&
                controller.getBoard().getNode(getFromNodeIndex() - 1).getNodeType() == playerTurn;
    }


}
