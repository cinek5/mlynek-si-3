package logic.moves;

import logic.Board;
import logic.Node;
import logic.NodeType;
import logic.controller.GameController;

/**
 * Created by Cinek on 24.04.2019.
 */
public class PlacingMove implements  Move{

    private int nodeIndex;
    private NodeType nodeType;



    @Override
    public boolean isLegal(GameController controller, NodeType playerTurn){
        Node node = controller.getBoard().getNode(nodeIndex);
        if (node.getNodeType()!=NodeType.NONE)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void makeMove(Board board) {
        int index = getNodeIndex();
        NodeType nodeType = getNodeType();
        board.getNodes().get(index-1).setNodeType(nodeType);

        if (nodeType == NodeType.BLACK)
        {
            board.setNumberOfBlackPiecesToBePlaced(board.getNumberOfBlackPiecesToBePlaced()-1);
            board.setNumberOfBlackPiecesOnBoard(board.getNumberOfBlackPiecesOnBoard()+1);

        }
        else
        {
            board.setNumberOfWhitePiecesToBePlaced(board.getNumberOfWhitePiecesToBePlaced()-1);
            board.setNumberOfWhitePiecesOnBoard(board.getNumberOfWhitePiecesOnBoard()+1);
        }
    }

    @Override
    public void undoMove(Board board) {
        int index = getNodeIndex();
        NodeType nodeType = getNodeType();
        board.getNodes().get(index-1).setNodeType(NodeType.NONE);

        if (nodeType == NodeType.BLACK)
        {
            board.setNumberOfBlackPiecesToBePlaced(board.getNumberOfBlackPiecesToBePlaced()+1);
            board.setNumberOfBlackPiecesOnBoard(board.getNumberOfBlackPiecesOnBoard()-1);

        }
        else
        {
            board.setNumberOfWhitePiecesToBePlaced(board.getNumberOfWhitePiecesToBePlaced()+1);
            board.setNumberOfWhitePiecesOnBoard(board.getNumberOfWhitePiecesOnBoard()-1);
        }

    }


    public NodeType getNodeType() {
        return nodeType;
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public PlacingMove(int nodeIndex, NodeType nodeType) {
        this.nodeIndex = nodeIndex;
        this.nodeType = nodeType;
    }

}
