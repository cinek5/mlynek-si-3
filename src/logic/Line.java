package logic;

/**
 * Created by Cinek on 24.04.2019.
 */
public class Line {

    private Node node1;
    private Node node2;
    private Node node3;
    private boolean millUsed;
    public Line(Node node1, Node node2, Node node3) {
        this.node1 = node1;
        node1.addLine(this);
        this.node2 = node2;
        node2.addLine(this);
        this.node3 = node3;
        node3.addLine(this);
    }


    public boolean checkMill(NodeType nodeType) {
        return checkMill() && node1.getNodeType() == nodeType;
    }

    public void setMillUsed()
    {
        millUsed = true;
    }

    public boolean isMillUsed()
    {
        return millUsed;
    }

    public boolean checkMill() {
        boolean hasMill = hasMill();
        if (!hasMill) {
            millUsed = false;
        }

        return hasMill;
    }

    public boolean hasMill(NodeType nodeType)
    {
        return hasMill() && node1.getNodeType() == nodeType;

    }

    private boolean hasMill()
    {
        boolean hasMill = node1.getNodeType() != NodeType.NONE &&
                node1.getNodeType() == node2.getNodeType() &&
                node2.getNodeType() == node3.getNodeType();

        return hasMill;

    }

    public  boolean isTwoPieceConfiguration(NodeType nodeType)
    {
        int numberOfPieces = 0;

        if (node1.getNodeType()==nodeType)
            numberOfPieces++;
        if (node2.getNodeType()==nodeType)
            numberOfPieces++;
        if (node3.getNodeType()==nodeType)
            numberOfPieces++;

        return numberOfPieces==2;
    }
}
