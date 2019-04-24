package logic;

/**
 * Created by Cinek on 24.04.2019.
 */
public class Node {
    private int index;
    private NodeType nodeType;
    private Position position;




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


}
