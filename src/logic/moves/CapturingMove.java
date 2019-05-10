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
        Node node = controller.getBoard().getNode(capturedNodeIndex);
        boolean isNodeOppositeColor = node.getNodeType()==capturedNodeType;
        boolean isInMill = node.getLines().stream()
                .filter(line -> line.wasMill(capturedNodeType)).findAny().isPresent();
        return isNodeOppositeColor && !isInMill;
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
