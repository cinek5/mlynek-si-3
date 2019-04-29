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
