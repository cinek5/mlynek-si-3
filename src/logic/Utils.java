package logic;

/**
 * Created by Cinek on 29.04.2019.
 */
public class Utils {
    public static NodeType getOppositeNodeType(NodeType nodeType)
    {
        if (nodeType==NodeType.NONE) throw new IllegalArgumentException("wrong node type");

        NodeType oppositeType;
        if (nodeType == NodeType.BLACK)
        {
            oppositeType = NodeType.WHITE;
        }
        else
        {
            oppositeType = NodeType.BLACK;
        }
        return oppositeType;
    }
}
