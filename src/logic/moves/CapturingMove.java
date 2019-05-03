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

    @Override
    public void makeMove(Board board) {
        int index = getCapturedNodeIndex();
        NodeType nodeType = getCapturedNodeType();
        board.getNodes().get(index-1).setNodeType(NodeType.NONE);
        if (nodeType == NodeType.BLACK)
        {
            board.setNumberOfBlackPiecesOnBoard(board.getNumberOfBlackPiecesOnBoard()-1);
        }
        else
        {
            board.setNumberOfWhitePiecesOnBoard(board.getNumberOfWhitePiecesOnBoard()-1);
        }
    }

    @Override
    public void undoMove(Board board) {
        int index = getCapturedNodeIndex();
        NodeType nodeType = getCapturedNodeType();
        board.getNodes().get(index-1).setNodeType(nodeType);
        if (nodeType == NodeType.BLACK)
        {
            board.setNumberOfBlackPiecesOnBoard(board.getNumberOfBlackPiecesOnBoard()+1);
        }
        else
        {
            board.setNumberOfWhitePiecesOnBoard(board.getNumberOfWhitePiecesOnBoard()+1);
        }

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
