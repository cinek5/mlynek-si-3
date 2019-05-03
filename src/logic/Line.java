package logic;

/**
 * Created by Cinek on 24.04.2019.
 */
public class Line {

    private Node node1;
    private Node node2;
    private Node node3;
    private boolean wasMill;

    public Line(Node node1, Node node2, Node node3) {
        this.node1 = node1;
        this.node2 = node2;
        this.node3 = node3;
        wasMill = false;
    }


    public boolean isMill(NodeType nodeType) {
        boolean isNewMill = !wasMill;
        boolean isMill = isNewMill && node1.getNodeType() == nodeType && node2.getNodeType() == nodeType && node3.getNodeType() == nodeType;
        wasMill = wasMill(NodeType.BLACK) || wasMill(NodeType.WHITE);
        return isMill;

    }

    private boolean wasMill(NodeType nodeType)
    {
        return node1.getNodeType() == nodeType && node2.getNodeType() == nodeType && node3.getNodeType() == nodeType;
    }
}
