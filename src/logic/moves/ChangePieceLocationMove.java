package logic.moves;

import logic.Board;
import logic.NodeType;

/**
 * Created by Cinek on 29.04.2019.
 */
public abstract class ChangePieceLocationMove implements Move {
    private int fromNodeIndex;
    private int toNodeIndex;
    private NodeType nodeType;

    public ChangePieceLocationMove(int fromNodeIndex, int toNodeIndex, NodeType nodeType) {
        this.fromNodeIndex = fromNodeIndex;
        this.toNodeIndex = toNodeIndex;
        this.nodeType = nodeType;
    }

    public int getFromNodeIndex() {
        return fromNodeIndex;
    }

    public void setFromNodeIndex(int fromNodeIndex) {
        this.fromNodeIndex = fromNodeIndex;
    }

    public int getToNodeIndex() {
        return toNodeIndex;
    }

    public void setToNodeIndex(int toNodeIndex) {
        this.toNodeIndex = toNodeIndex;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    @Override
    public void makeMove(Board board)
    {
        int fromIndex = getFromNodeIndex();
        int toIndex = getToNodeIndex();
        board.getNodes().get(fromIndex-1).setNodeType(NodeType.NONE);
        board.getNodes().get(toIndex-1).setNodeType( getNodeType() );
    }

    @Override
    public void undoMove(Board board)
    {
        int fromIndex = getFromNodeIndex();
        int toIndex = getToNodeIndex();
        board.getNodes().get(fromIndex-1).setNodeType( getNodeType() );
        board.getNodes().get(toIndex-1).setNodeType( NodeType.NONE );
    }
}
