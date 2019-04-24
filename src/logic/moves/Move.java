package logic.moves;

import logic.Board;
import logic.controller.PlayerTurn;

/**
 * Created by Cinek on 24.04.2019.
 */
public interface Move {
    String getRepresentation();
    boolean isLegal(Board board, PlayerTurn playerTurn);
}
