package logic.moves;

import logic.Board;
import logic.Node;
import logic.controller.PlayerTurn;

/**
 * Created by Cinek on 24.04.2019.
 */
public class MovingMove implements Move {
    private int fromNodeIndex;
    private int toNodeIndex;

    @Override
    public String getRepresentation() {
        return null;
    }

    @Override
    public boolean isLegal(Board board, PlayerTurn playerTurn) {
        return false;
    }

    public MovingMove(int fromNodeIndex, int toNodeIndex) {
        this.fromNodeIndex = fromNodeIndex;
        this.toNodeIndex = toNodeIndex;
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
}
