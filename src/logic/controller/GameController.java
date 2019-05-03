package logic.controller;

import logic.*;
import logic.moves.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Created by Cinek on 24.04.2019.
 */
public class GameController {
    private static final int BOARD_NUMBER_OF_PIECES=24;

    private Queue<Move> movesHistoryWhite;
    private Queue<Move> movesHistoryBlack;
    private NodeType playerTurn;
    private Phase gamePhase;
    private boolean wasMillInPreviousTurn;
    private Board board;

    private Move lastNonCapturingMoveForWhite;
    private Move lastNonCapturingMoveForBlack;

    private GameInput whiteInput;
    private GameInput blackInput;



    public Queue<Move> getMovesHistoryWhite() {
        return movesHistoryWhite;
    }

    public Queue<Move> getMovesHistoryBlack() {
        return movesHistoryBlack;
    }

    public NodeType getPlayerTurn() {
        return playerTurn;
    }

    public Phase getGamePhase() {
        return gamePhase;
    }

    public boolean isWasMillInPreviousTurn() {
        return wasMillInPreviousTurn;
    }

    public GameController(NodeType firstPlayerTurn, GameInput whiteInput, GameInput blackInput) {
        this.movesHistoryWhite = new ArrayDeque<>();
        this.movesHistoryBlack = new ArrayDeque<>();
        gamePhase = Phase.PLACING;
        this.playerTurn = firstPlayerTurn;
        this.whiteInput = whiteInput;
        this.blackInput = blackInput;
        this.board = new Board();

    }


    public void game() {
        while (!someoneWon()) {

            board.printBoard();

            setGamePhase();
            System.out.println(gamePhase);

            boolean validMove = false;
            Move nextMove = null;

            System.out.println("Ruch gracza: "+playerTurn);
            while(!validMove) {
                if (playerTurn == NodeType.WHITE) {
                    nextMove = whiteInput.getMove(this);

                } else {
                    nextMove = blackInput.getMove(this);
                }
                if (nextMove.isLegal(this, playerTurn))
                {
                    validMove = true;
                }
            }

            nextMove.makeMove(board);


            saveMove(nextMove);



            int mills = board.countMills(playerTurn);

            if (mills>0)
            {
                System.out.println("MLYNEK");
            }
            else
            {
                switchPlayerTurn();
            }







            wasMillInPreviousTurn = mills>0;






        }


    }

    public List<Move> generatePossibleMoves()
    {
        return null;
    }

    public List<Move> generatePossiblePlacingMoves(NodeType playerNodeType)
    {
        List<Move> moves = new ArrayList<>();
        for (int i=1; i<=BOARD_NUMBER_OF_PIECES; i++)
        {
            if (board.getNodes().get(i-1).getNodeType()==NodeType.NONE)
            {
                moves.add(new PlacingMove(i, playerNodeType));
            }
        }
        return moves;
    }

    public List<Move> generatePossibleCapturingMoves(NodeType playerNodeType)
    {
        List<Move> moves = new ArrayList<>();
        NodeType oppositeNodeType = Utils.getOppositeNodeType(playerNodeType);
        for (int i=1; i<=BOARD_NUMBER_OF_PIECES; i++)
        {
            if (board.getNodes().get(i-1).getNodeType()==oppositeNodeType)
            {
                moves.add(new CapturingMove(i,oppositeNodeType ));
            }
        }
        return moves;
    }
    public List<Move> generatePossibleSlidingMoves(NodeType playerNodeType)
    {
        List<Move> moves = new ArrayList<>();
        List<Node> playerNodes = board.getNodes().stream().filter(node -> node.getNodeType()==playerNodeType)
                .collect(Collectors.toList());
        for (Node node: playerNodes)
        {
            for (int index: node.getAdjacentNodesIndexes())
            {
                if (!board.getNodes().get(index).isOccupied())
                {
                    int fromIndex = node.getIndex();
                    int toIndex = index+1;

                    if (!wasNodeHereInPreviousMove(playerNodeType, toIndex))
                    {
                        moves.add(new SlidingMove(fromIndex, toIndex, playerNodeType));
                    }
                }
            }
        }

        return moves;

    }

    public List<Move> generatePossibleMovingMoves(NodeType playerNodeType)
    {
        List<Move> moves = new ArrayList<>();
        List<Node> playerNodes = board.getNodes().stream().filter( node -> node.getNodeType()==playerNodeType)
                .collect(Collectors.toList());Collectors.toList();

        List<Integer> freeNodeIndexes = board.getNodes().stream().filter(node->node.getNodeType()==NodeType.NONE)
                .map( node -> node.getIndex()).collect(Collectors.toList());

        for (Node node: playerNodes)
        {
            for (Integer toIndex : freeNodeIndexes)
            {
                int fromIndex = node.getIndex();
                if (!wasNodeHereInPreviousMove(playerNodeType, toIndex))
                {
                    moves.add(new MovingMove(fromIndex, toIndex, playerNodeType));
                }
            }
        }
        return moves;
    }

    public boolean hasLostByPiecesCapture(NodeType nodeType)
    {
        if (nodeType == NodeType.WHITE)
        {
            return board.getNumberOfWhitePiecesToBePlaced()==0 && board.getNumberOfWhitePiecesOnBoard()<=2;
        }
        else
        {
            return board.getNumberOfBlackPiecesToBePlaced()==0 && board.getNumberOfBlackPiecesOnBoard()<=2;
        }
    }

    public boolean wasNodeHereInPreviousMove(NodeType nodeType, int index) {
        Move previousMove;
        if (nodeType == NodeType.BLACK) {
            previousMove = lastNonCapturingMoveForBlack;
        } else {
            previousMove = lastNonCapturingMoveForWhite;
        }

        if (previousMove == null)
        {
            return false;
        }
        int lastIndex = -1;
        if (previousMove instanceof PlacingMove)
        {
            lastIndex = ((PlacingMove) previousMove).getNodeIndex();
        }
        if (previousMove instanceof ChangePieceLocationMove)
        {
            lastIndex = ((ChangePieceLocationMove)previousMove).getFromNodeIndex();
        }

        return lastIndex==index;


    }

    public Board getBoard() {
        return board;
    }

    private void saveMove(Move move)
    {
        if (playerTurn==NodeType.WHITE)
        {
            movesHistoryWhite.add(move);
            if (!(move instanceof CapturingMove))
            {
                lastNonCapturingMoveForWhite = move;
            }
        } else {
            movesHistoryBlack.add(move);
            if (!(move instanceof CapturingMove))
            {
                lastNonCapturingMoveForBlack = move;
            }
        }

    }

    private Phase setGamePhase()
    {
        if (isMovingFreelyForPlayer(playerTurn))
        {
            gamePhase = Phase.PLACING;
        }
        else if (isSlidingPhase())
        {
            gamePhase = Phase.SLIDING;
        }
        else
        {
            gamePhase = Phase.PLACING;
        }
        return gamePhase;
    }
    private boolean isMovingFreelyForPlayer(NodeType playerTurn)
    {
        if (playerTurn == NodeType.WHITE)
        {
            return isSlidingPhase() && board.getNumberOfWhitePiecesOnBoard()<=3;
        }
        else
        {
            return isSlidingPhase() && board.getNumberOfBlackPiecesOnBoard()<=3;
        }
    }

    private boolean isSlidingPhase()
    {
        if (playerTurn==NodeType.BLACK)
        {
            return board.getNumberOfBlackPiecesToBePlaced()==0;
        }
        else
        {
            return board.getNumberOfWhitePiecesToBePlaced()==0;
        }
    }

    private void switchPlayerTurn()
    {
        playerTurn = Utils.getOppositeNodeType(playerTurn);
    }

    private boolean someoneWon() {
        if (hasLostByPiecesCapture(NodeType.BLACK))
        {
           System.out.println("WHITE HAS WON! Black has too few pieces.");
           return true;
        }
        if (hasLostByPiecesCapture(NodeType.WHITE))
        {
            System.out.println("BLACK HAS WON!!! White has too few pieces.");
            return true;
        }

        if (isBlocked(NodeType.BLACK))
        {
            System.out.println("White has won! Black is blocked.");
            return true;
        }
        if (isBlocked(NodeType.WHITE))
        {
            System.out.println("Black has won! White is blocked.");
            return true;
        }

        return false;
    }

    private boolean isBlocked(NodeType nodeType)
    {
        return board.isPlayerBlocked(nodeType);
    }
}
