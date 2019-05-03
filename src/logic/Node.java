package logic;

import java.util.List;

/**
 * Created by Cinek on 24.04.2019.
 */
public class Node {
    private int index;
    private NodeType nodeType;
    private Position position;
    private List<Integer> adjacentNodesIndexes;




    public Node(int index) {
        this.index = index;
        this.nodeType = NodeType.NONE;
    }

    public Node(int index, int x, int y) {
        this(index);
        Position position = new Position(x,y);
        this.position = position;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }
    public NodeType getNodeType()
    {
      return nodeType;
    }

    public List<Integer> getAdjacentNodesIndexes() {
        return adjacentNodesIndexes;
    }

    public void setAdjacentNodesIndexes(List<Integer> adjacentNodesIndexes) {
        this.adjacentNodesIndexes = adjacentNodesIndexes;
    }

    public boolean isOccupied()
    {
        return nodeType!=NodeType.NONE;
    }

    @Override
    public String toString() {
        if (nodeType==NodeType.WHITE) return " W";
        else if (nodeType==NodeType.BLACK) return " B";
        else return String.format("%2d", index);
    }


}
