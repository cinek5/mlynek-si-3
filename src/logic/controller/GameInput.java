package logic.controller;

import logic.moves.Move;

/**
 * Created by Cinek on 24.04.2019.
 */
public interface GameInput {
    Move getMove(GameController controller);

}
