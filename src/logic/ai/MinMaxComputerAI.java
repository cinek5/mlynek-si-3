package logic.ai;

import logic.NodeType;
import logic.Phase;
import logic.Utils;
import logic.controller.GameController;
import logic.evaluation.GameStateEvaluator;
import logic.moves.Move;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Cinek on 07.05.2019.
 */
public class MinMaxComputerAI implements ComputerAI {




    private GameStateEvaluator gameStateEvaluator;
    private NodeType playerNodeType;
    private int maxDepth;


    public MinMaxComputerAI(GameStateEvaluator gameStateEvaluator, NodeType playerNodeType, int depth)
    {
        this.gameStateEvaluator = gameStateEvaluator;
        this.playerNodeType = playerNodeType;
        this.maxDepth = depth;

    }


    @Override
    public Move getBestPossibleMove(GameController gameController) {
        List<Move> possibleMoves = gameController.generatePossibleMoves(playerNodeType);

        if (possibleMoves.isEmpty())
        {
            return null;
        }

        Iterator<Move> movesIt = possibleMoves.iterator();

        Move bestMove = movesIt.next();

        GameController.GameStateHelper gameStateHelper = gameController.playMove( bestMove );

        int bestVal = minimax(gameController, 0, true );

        gameController.undoMove( bestMove, gameStateHelper );

        while(movesIt.hasNext())
        {
            Move move = movesIt.next();

            GameController.GameStateHelper gameState = gameController.playMove(move);

            int val = minimax(gameController, 0, true );

            if (val>bestVal)
            {
                bestMove = move;
                bestVal = val;
            }

            gameController.undoMove( move, gameState );

        }


        return bestMove;
    }

    private int evaluate(GameController gameController, NodeType nodeType)
    {
        Phase phase = gameController.getGamePhase(nodeType);
        int val =0;
        if (phase.equals(Phase.PLACING))
        {
            val = gameStateEvaluator.evaluatePlacingPhase(gameController, nodeType);
        }
        if (phase.equals(Phase.SLIDING))
        {
            val = gameStateEvaluator.evaluateSlidingPhase(gameController, nodeType);
        }
        if (phase.equals(Phase.MOVING_FREELY))
        {
            val = gameStateEvaluator.evaluateMovingPhase(gameController, nodeType);
        }

        return val;
    }

    private int minimax(GameController gameController, int depth, boolean isMaximizingPlayer)
    {
        if (depth == maxDepth )
        {
            return evaluate(gameController, playerNodeType);
        }

        if (isMaximizingPlayer)
        {
            int bestVal = Integer.MIN_VALUE;
            List<Move> possibleMoves = gameController.generatePossibleMoves(playerNodeType);

            if (possibleMoves.isEmpty())
            {
                return bestVal;
            }

            for (Move move: possibleMoves)
            {
                GameController.GameStateHelper gameState = gameController.playMove(move);

                int val = minimax(gameController, depth+1, false);

                bestVal = Math.max(bestVal, val);

                gameController.undoMove(move, gameState);
            }





            return bestVal;

        }
        else
        {

            int bestVal = Integer.MAX_VALUE;

            List<Move> possibleMoves = gameController.generatePossibleMoves(Utils.getOppositeNodeType(playerNodeType));

            if (possibleMoves.isEmpty())
            {
                return bestVal;
            }

            for (Move move: possibleMoves)
            {
                GameController.GameStateHelper gameState = gameController.playMove(move);

                int val = minimax(gameController, depth+1, true);

                bestVal = Math.min(bestVal, val);

                gameController.undoMove(move, gameState);
            }


            return bestVal;

        }


    }


}
