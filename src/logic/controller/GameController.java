package logic.controller;

import logic.Phase;
import logic.moves.MovingMove;

import java.util.Queue;

/**
 * Created by Cinek on 24.04.2019.
 */
public class GameController {
    private Queue<MovingMove> movesHistory;
    private PlayerTurn playerTurn;
    private Phase gamePhase;
    private boolean wasMillInPreviousTurn;

    private GameInput whiteInput;
    private GameInput blackInput;


    public Queue<MovingMove> getMovesHistory() {
        return movesHistory;
    }

    public PlayerTurn getPlayerTurn() {
        return playerTurn;
    }

    public Phase getGamePhase() {
        return gamePhase;
    }

    public boolean isWasMillInPreviousTurn() {
        return wasMillInPreviousTurn;
    }
}
