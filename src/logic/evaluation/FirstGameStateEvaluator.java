package logic.evaluation;

import logic.*;
import logic.controller.GameController;

import java.util.List;

/**
 * Created by Cinek on 06.05.2019.
 */
public class FirstGameStateEvaluator implements GameStateEvaluator {


    @Override
    public int evaluatePlacingPhase(GameController gameController, NodeType playerTurn) {


        List<Node> nodes = gameController.getBoard().getNodes();

       // int numOfWhites = countNodesOfType(nodes, NodeType.WHITE);
       // int numOfBlacks = countNodesOfType(nodes, NodeType.BLACK);

        int countMills = howManyMills(gameController.getBoard(), playerTurn);
        int oppositeMills = howManyMills(gameController.getBoard(), Utils.getOppositeNodeType(playerTurn));

        return 20*countMills - 10*oppositeMills;


    }

    private int howManyMills(Board board, NodeType nodeType)
    {
        int count = 0;
        for (Line line : board.getLines())
        {
            if (line.hasMill(nodeType))
            {
                count++;
            }
        }
        return count;
    }


    @Override
    public int evaluateSlidingPhase(GameController gameController, NodeType playerTurn) {
        return evaluateEndgamePhase(gameController, playerTurn);
    }

    @Override
    public int evaluateMovingPhase(GameController gameController, NodeType playerTurn) {
        return evaluateEndgamePhase(gameController, playerTurn);
    }

    private int evaluateEndgamePhase(GameController gameController, NodeType playerTurn)
    {
        List<Node> nodes = gameController.getBoard().getNodes();
        int numOfWhites = countNodesOfType(nodes, NodeType.WHITE);
        int numOfBlacks = countNodesOfType(nodes, NodeType.BLACK);

        int winningWeight = 10000;
        int multiply = 1000;

        if (playerTurn.equals(NodeType.BLACK))
        {
            if (numOfWhites <= 2) return winningWeight;

            int whiteMoves = 0;
            Phase gamePhaseForWhite = gameController.getGamePhase(NodeType.WHITE);
            if (gamePhaseForWhite.equals(Phase.SLIDING))
            {
                whiteMoves = gameController.generatePossibleSlidingMoves(NodeType.WHITE).size();
            }
            else if (gamePhaseForWhite.equals(Phase.MOVING_FREELY))
            {
                whiteMoves = gameController.generatePossibleMovingMoves(NodeType.WHITE).size();
            }
            int capturingMovesForWhite = gameController.generatePossibleCapturingMoves(NodeType.WHITE).size();

            return multiply*(numOfBlacks-numOfWhites) - whiteMoves - 5*capturingMovesForWhite;

        } //for whites
        else
        {
            if (numOfBlacks <= 2) return winningWeight;

            int blackMoves = 0;
            Phase gamePhaseForBlack = gameController.getGamePhase(NodeType.BLACK);
            if (gamePhaseForBlack.equals(Phase.SLIDING))
            {
                blackMoves = gameController.generatePossibleSlidingMoves(NodeType.BLACK).size();
            }
            else if (gamePhaseForBlack.equals(Phase.MOVING_FREELY))
            {
                blackMoves = gameController.generatePossibleMovingMoves(NodeType.BLACK).size();
            }
            int capturingMovesForBlack = gameController.generatePossibleCapturingMoves(NodeType.BLACK).size();

            return multiply*(numOfWhites-numOfBlacks) - blackMoves - 5*capturingMovesForBlack;

        }


    }

    private int countNodesOfType(List<Node> nodes, NodeType nodeType )
    {
        int numOfNodes = 0;
        for(Node node: nodes)
        {
            if (node.getNodeType().equals(nodeType))
            {
                numOfNodes++;
            }


        }

        return numOfNodes;
    }
}
