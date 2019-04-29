package logic.controller;

import logic.NodeType;
import logic.moves.*;

import java.util.Scanner;

/**
 * Created by Cinek on 24.04.2019.
 */
public class MoveParser {

    public PlacingMove parsePlacingMove(String line, NodeType nodeType) {
        int nodeIndex = Integer.parseInt(line);
        return new PlacingMove(nodeIndex, nodeType);
    }

    public CapturingMove parseCapturingMove(String line, NodeType nodeType) {
        int nodeIndex = Integer.parseInt(line);
        return new CapturingMove(nodeIndex, nodeType);
    }

    public MovingMove parseMovingMove(String line, NodeType nodeType) {
        Scanner reader = new Scanner(line);
        int fromIndex = reader.nextInt();
        int toIndex = reader.nextInt();
        return new MovingMove(fromIndex, toIndex, nodeType);
    }

    public SlidingMove parseSlidingMove(String line, NodeType nodeType) {
        Scanner reader = new Scanner(line);
        int fromIndex = reader.nextInt();
        int toIndex = reader.nextInt();
        return new SlidingMove(fromIndex, toIndex, nodeType);
    }


}
