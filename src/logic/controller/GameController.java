package logic.controller;

import logic.Board;
import logic.NodeType;
import logic.Phase;
import logic.Utils;
import logic.moves.*;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Cinek on 24.04.2019.
 */
public class GameController {
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
