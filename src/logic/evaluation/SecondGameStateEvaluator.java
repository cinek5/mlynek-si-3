package logic.evaluation;

import javafx.scene.control.TextFormatter;
import logic.*;
import logic.controller.GameController;
import logic.moves.ChangePieceLocationMove;
import logic.moves.Move;
import logic.moves.PlacingMove;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Created by Cinek on 06.05.2019.
 */
public class SecondGameStateEvaluator implements GameStateEvaluator {


    private static int CLOSED_MORRIS_WEIGHT_PHASE_1 = 40;
    private static int CLOSED_MORRIS_WEIGHT_PHASE_2 = 14;
    private static int CLOSED_MORRIS_WEIGHT_PHASE_3 = 16;

    private static int NUMBER_OF_MORRISES_PHASE_1 = 26;
    private static int NUMBER_OF_MORRISES_PHASE_2 = 43;
    private static int NUMBER_OF_MORRISES_PHASE_3 = 0;

    private static int NUMBER_OF_BLOCKED_OPPONENT_PIECES_PHASE_1 = 1;
    private static int NUMBER_OF_BLOCKED_OPPONENT_PIECES_PHASE_2 = 10;
    private static int NUMBER_OF_BLOCKED_OPPONENT_PIECES_PHASE_3 = 0;


    private static int NUMBER_OF_PIECES_PHASE_1 = 9;
    private static int NUMBER_OF_PIECES_PHASE_2 = 11;
    private static int NUMBER_OF_PIECES_PHASE_3 = 0;

    private static int NUMBER_OF_2_PIECE_CONFIGURATIONS_PHASE_1 = 10;
    private static int NUMBER_OF_2_PIECE_CONFIGURATIONS_PHASE_2 = 0;
    private static int NUMBER_OF_2_PIECE_CONFIGURATIONS_PHASE_3 = 10;

    private static int NUMBER_OF_3_PIECE_CONFIGURATIONS_PHASE_1 = 7;
    private static int NUMBER_OF_3_PIECE_CONFIGURATIONS_PHASE_2 = 0;
    private static int NUMBER_OF_3_PIECE_CONFIGURATIONS_PHASE_3 = 1;

    private static int NUMBER_OF_WINNING_CONFIGURATION_PHASE_1 = 0;
    private static int NUMBER_OF_WINNING_CONFIGURATION_PHASE_2 = 1086;
    private static int NUMBER_OF_WINNING_CONFIGURATION_PHASE_3 = 1190;

    public SecondGameStateEvaluator(List<ArrayList<Integer>> weights) {

        if (weights!=null) {
            CLOSED_MORRIS_WEIGHT_PHASE_1 = weights.get(0).get(0);
            NUMBER_OF_MORRISES_PHASE_1 = weights.get(0).get(1);
            NUMBER_OF_BLOCKED_OPPONENT_PIECES_PHASE_1 = weights.get(0).get(2);
            NUMBER_OF_PIECES_PHASE_1 = weights.get(0).get(3);
            NUMBER_OF_2_PIECE_CONFIGURATIONS_PHASE_1 = weights.get(0).get(4);
            NUMBER_OF_3_PIECE_CONFIGURATIONS_PHASE_1 = weights.get(0).get(5);


            CLOSED_MORRIS_WEIGHT_PHASE_2 = weights.get(1).get(0);
            NUMBER_OF_MORRISES_PHASE_2 = weights.get(1).get(1);
            NUMBER_OF_BLOCKED_OPPONENT_PIECES_PHASE_2 = weights.get(1).get(2);
            NUMBER_OF_PIECES_PHASE_2 = weights.get(1).get(3);
            NUMBER_OF_WINNING_CONFIGURATION_PHASE_2 = weights.get(1).get(4);
            NUMBER_OF_2_PIECE_CONFIGURATIONS_PHASE_2 = weights.get(1).get(5);


            CLOSED_MORRIS_WEIGHT_PHASE_3 = weights.get(2).get(0);
            NUMBER_OF_2_PIECE_CONFIGURATIONS_PHASE_3 = weights.get(2).get(1);
            NUMBER_OF_3_PIECE_CONFIGURATIONS_PHASE_3 = weights.get(2).get(2);
            NUMBER_OF_WINNING_CONFIGURATION_PHASE_3 = weights.get(2).get(3);
        }








    }


    @Override
    public int evaluatePlacingPhase(GameController gameController, NodeType playerTurn) {
        int numberOfClosedMorrises = numberOfClosedMorrises(gameController, playerTurn);
        int numberOfMorrisesPlayer = howManyMills(gameController.getBoard(), playerTurn);
        int numberOfMorrisesOpponent = howManyMills(gameController.getBoard(), Utils.getOppositeNodeType(playerTurn));

        int numberOfPlayerPieces = countNodesOfType(gameController.getBoard().getNodes(), playerTurn);
        int numberOfOpponentPieces = countNodesOfType(gameController.getBoard().getNodes(), Utils.getOppositeNodeType(playerTurn));
        int numOfPiecesDiff = numberOfPlayerPieces - numberOfOpponentPieces;

        int numberOfBlockedOpponent = numberOfBlockedOpponentPieces(gameController.getBoard(), playerTurn);

        int numberOf2pieceConfs = numberOfTwoPieceConfigurations(gameController.getBoard(), playerTurn);

        int numberOf3pieceConfs = numberOfThreePieceConfigurations(gameController.getBoard(), playerTurn);

        return CLOSED_MORRIS_WEIGHT_PHASE_1 * numberOfClosedMorrises +
                NUMBER_OF_PIECES_PHASE_1 * numOfPiecesDiff + NUMBER_OF_MORRISES_PHASE_1 * (numberOfMorrisesPlayer - numberOfMorrisesOpponent) +
                NUMBER_OF_BLOCKED_OPPONENT_PIECES_PHASE_1 * numberOfBlockedOpponent + NUMBER_OF_2_PIECE_CONFIGURATIONS_PHASE_1 * numberOf2pieceConfs +
                numberOf3pieceConfs * NUMBER_OF_3_PIECE_CONFIGURATIONS_PHASE_1;

    }


    @Override
    public int evaluateSlidingPhase(GameController gameController, NodeType playerTurn) {
        int numberOfClosedMorrises = numberOfClosedMorrises(gameController, playerTurn);
        int numberOfMorrisesPlayer = howManyMills(gameController.getBoard(), playerTurn);
        int numberOfMorrisesOpponent = howManyMills(gameController.getBoard(), Utils.getOppositeNodeType(playerTurn));

        int numberOfPlayerPieces = countNodesOfType(gameController.getBoard().getNodes(), playerTurn);
        int numberOfOpponentPieces = countNodesOfType(gameController.getBoard().getNodes(), Utils.getOppositeNodeType(playerTurn));
        int numOfPiecesDiff = numberOfPlayerPieces - numberOfOpponentPieces;
        int numberOf2pieceConfs = numberOfTwoPieceConfigurations(gameController.getBoard(), playerTurn);

        int numberOfBlockedOpponent = numberOfBlockedOpponentPieces(gameController.getBoard(), playerTurn);

        return CLOSED_MORRIS_WEIGHT_PHASE_2 * numberOfClosedMorrises + NUMBER_OF_MORRISES_PHASE_2 * (numberOfMorrisesPlayer - numberOfMorrisesOpponent) +
                NUMBER_OF_BLOCKED_OPPONENT_PIECES_PHASE_2 * numberOfBlockedOpponent +
                NUMBER_OF_PIECES_PHASE_2 * numOfPiecesDiff + NUMBER_OF_WINNING_CONFIGURATION_PHASE_2 * winningConfiguration(numberOfPlayerPieces, numberOfOpponentPieces)
                + NUMBER_OF_2_PIECE_CONFIGURATIONS_PHASE_2 * numberOf2pieceConfs;

    }

    @Override
    public int evaluateMovingPhase(GameController gameController, NodeType playerTurn) {
        return evaluateEndgamePhase(gameController, playerTurn);
    }

    private int evaluateEndgamePhase(GameController gameController, NodeType playerTurn) {
        int numberOf2PieceConf = numberOfTwoPieceConfigurations(gameController.getBoard(), playerTurn);
        int numberOf3PieceConf = numberOfThreePieceConfigurations(gameController.getBoard(), playerTurn);

        int numberOfPlayerPieces = countNodesOfType(gameController.getBoard().getNodes(), playerTurn);
        int numberOfOpponentPieces = countNodesOfType(gameController.getBoard().getNodes(), Utils.getOppositeNodeType(playerTurn));
        int numberOfClosedMorrises = numberOfClosedMorrises(gameController, playerTurn);

        int winning = winningConfiguration(numberOfPlayerPieces, numberOfOpponentPieces);

        return NUMBER_OF_2_PIECE_CONFIGURATIONS_PHASE_3 * numberOf2PieceConf + NUMBER_OF_3_PIECE_CONFIGURATIONS_PHASE_3 * numberOf3PieceConf
                + NUMBER_OF_WINNING_CONFIGURATION_PHASE_3 * winning + CLOSED_MORRIS_WEIGHT_PHASE_3 * numberOfClosedMorrises;
    }

    private int howManyMills(Board board, NodeType nodeType) {
        int count = 0;
        for (Line line : board.getLines()) {
            if (line.hasMill(nodeType)) {
                count++;
            }
        }
        return count;
    }

    private int numberOfBlockedOpponentPieces(Board board, NodeType nodeType) {
        int numOfBlockedPieces = 0;

        for (Node node : board.getNodes()) {
            if (node.getNodeType() == Utils.getOppositeNodeType(nodeType)) {
                boolean blocked = true;
                for (Integer index : node.getAdjacentNodesIndexes()) {
                    if (!board.getNodes().get(index).isOccupied()) {
                        blocked = false;
                    }
                }

                if (blocked) {
                    numOfBlockedPieces++;
                }
            }
        }

        return numOfBlockedPieces;
    }

    private int numberOfTwoPieceConfigurations(Board board, NodeType nodeType) {
        int numOfTwoPieceConfigurations = 0;

        List<Line> lines = board.getLines();

        for (Line line : lines) {
            if (line.isTwoPieceConfiguration(nodeType)) {
                numOfTwoPieceConfigurations++;
            }
        }
        return numOfTwoPieceConfigurations;
    }

    private int numberOfThreePieceConfigurations(Board board, NodeType nodeType) {
        List<Integer> cornerNodesIndexes = asList(1, 3, 4, 6, 7, 9, 16, 18, 19, 21, 22, 24);
        int numberOfThreePieceConfigurations = 0;

        for (Integer index : cornerNodesIndexes) {
            Node node = board.getNode(index);
            if (node.getNodeType() == nodeType) {
                int countAdjMatchingNodeType = 0;
                for (Integer adjIndex : node.getAdjacentNodesIndexes()) {
                    Node adjNode = board.getNode(adjIndex);
                    if (adjNode.getNodeType() == nodeType) {
                        countAdjMatchingNodeType++;
                    }
                }
                if (countAdjMatchingNodeType == 2) {
                    numberOfThreePieceConfigurations++;
                }
            }
        }


        return numberOfThreePieceConfigurations;
    }

    private int numberOfClosedMorrises(GameController gameController, NodeType playerNode) {
        Move lastMovePlayerBlack = gameController.getLastNonCapturingMoveForBlack();
        Move lastMovePlayerWhite = gameController.getLastNonCapturingMoveForWhite();

        int closedMillsBlack = 0;
        int closedMillsWhite = 0;

        if (lastMovePlayerBlack instanceof ChangePieceLocationMove) {
            ChangePieceLocationMove blackMove = (ChangePieceLocationMove) (lastMovePlayerBlack);
            int index = blackMove.getToNodeIndex();
            closedMillsBlack += getNumberOfMills(gameController.getBoard(), index, NodeType.BLACK);

        } else if (lastMovePlayerBlack instanceof PlacingMove) {
            PlacingMove move = (PlacingMove) lastMovePlayerBlack;
            closedMillsBlack += getNumberOfMills(gameController.getBoard(), move.getNodeIndex(), NodeType.BLACK);

        }

        if (lastMovePlayerWhite instanceof ChangePieceLocationMove) {
            ChangePieceLocationMove whiteMove = (ChangePieceLocationMove) (lastMovePlayerWhite);
            int index = whiteMove.getToNodeIndex();
            closedMillsWhite += getNumberOfMills(gameController.getBoard(), index, NodeType.WHITE);

        } else if (lastMovePlayerWhite instanceof PlacingMove) {
            PlacingMove move = (PlacingMove) lastMovePlayerWhite;
            closedMillsWhite += getNumberOfMills(gameController.getBoard(), move.getNodeIndex(), NodeType.WHITE);

        }

        if (playerNode == NodeType.WHITE) {
            return closedMillsWhite - closedMillsBlack;
        } else {
            return closedMillsBlack - closedMillsWhite;
        }
    }

    private int getNumberOfMills(Board board, int index, NodeType nodeType) {
        int numberOfMills = 0;
        Node node = board.getNode(index);
        for (Line line : node.getLines()) {
            if (line.hasMill(nodeType)) {
                numberOfMills++;
            }
        }

        return numberOfMills;

    }

    private int winningConfiguration(int playerMills, int opponentMills) {
        if (playerMills <= 2) {
            return -1;
        }
        if (opponentMills <= 2) {
            return 1;
        }

        return 0;
    }

    private int countNodesOfType(List<Node> nodes, NodeType nodeType) {
        int numOfNodes = 0;
        for (Node node : nodes) {
            if (node.getNodeType().equals(nodeType)) {
                numOfNodes++;
            }


        }

        return numOfNodes;
    }
}
