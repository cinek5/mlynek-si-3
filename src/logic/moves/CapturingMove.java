package logic.moves;

import logic.Board;
import logic.Node;
import logic.NodeType;
import logic.controller.PlayerTurn;

/**
 * Created by Cinek on 24.04.2019.
 */
public class CapturingMove implements Move {

    private int capturedNodeIndex;
    private NodeType capturedNodeType;

    @Override
    public String getRepresentation() {
        return null;
    }

    @Override
    public boolean isLegal(Board board, PlayerTurn playerTurn) {
        return false;
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
