package logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cinek on 24.04.2019.
 */
public class Node {
    private int index;
    private NodeType nodeType;
    private List<Line> lines;
    private List<Integer> adjacentNodesIndexes;


    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";




    public Node(int index) {
        this.index = index;
        this.nodeType = NodeType.NONE;
        lines = new ArrayList<>(3);

    }

    public List<Line> getLines() {
        return lines;
    }

    public void addLine(Line line)
    {
        this.lines.add(line);
    }

    public int getIndex() {
        return index;
    }


    public void setIndex(int index) {
        this.index = index;
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
        String ind = String.format("%2d", index);
        if (nodeType==NodeType.WHITE) return ANSI_YELLOW+ind+ANSI_RESET;
        else if (nodeType==NodeType.BLACK) return ANSI_BLUE+ind+ANSI_RESET;
        else return ind;
    }


}
