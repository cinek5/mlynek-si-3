package logic.controller;

import logic.NodeType;
import logic.moves.CapturingMove;
import logic.moves.Move;
import logic.moves.MovingMove;
import logic.moves.PlacingMove;

/**
 * Created by Cinek on 24.04.2019.
 */
public class MoveParser {

    public PlacingMove parsePlacingMove(String line, NodeType nodeType)
    {
        int nodeIndex = Integer.parseInt(line);
        return new PlacingMove(nodeIndex, nodeType );
    }
    public CapturingMove parseCapturingMove(String line, NodeType nodeType )
    {
        int nodeIndex = Integer.parseInt(line);
        return new CapturingMove(nodeIndex, nodeType);
    }
    public MovingMove parseMovingMove(String line, NodeType nodeType )
    {
        return null;
    }
}
