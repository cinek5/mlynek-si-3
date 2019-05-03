package logic.moves;

import logic.Board;
import logic.Node;
import logic.NodeType;
import logic.controller.GameController;

/**
 * Created by Cinek on 24.04.2019.
 */
public class CapturingMove implements Move {

    private int capturedNodeIndex;
    private NodeType capturedNodeType;

    @Override
    public boolean isLegal(GameController controller, NodeType playerTurn) {
        boolean isNodeOppositeColor = controller.getBoard().getNode(capturedNodeIndex).getNodeType()==capturedNodeType;
        return isNodeOppositeColor;
    }

    public int getCapturedNodeIndex() {
        return capturedNodeIndex;
    }

    public void setCapturedNodeIndex(int capturedNodeIndex) {
        this.capturedNodeIndex = capturedNodeIndex;
    }

    public NodeType getCapturedNodeType() {
        return capturedNodeType;
    }

    public void setCapturedNodeType(NodeType capturedNodeType) {
        this.capturedNodeType = capturedNodeType;
    }

    public CapturingMove(int capturedNodeIndex, NodeType capturedNodeType) {
        this.capturedNodeIndex = capturedNodeIndex;
        this.capturedNodeType = capturedNodeType;
    }
}
