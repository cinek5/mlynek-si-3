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
    private Move lastNonCapturingMOveForBlack;

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
        gamePhase = Phase.PLACIING;
        this.playerTurn = firstPlayerTurn;
        this.whiteInput = whiteInput;
        this.blackInput = blackInput;
        this.board = new Board();

    }


    public void game() {
        while (!someoneWon()) {

            board.printBoard();

            setGamePhase();

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

            board.makeMove(nextMove);


            saveMove(nextMove);



            switchPlayerTurn();



            int blackMills = board.countMills(NodeType.BLACK);
            int whiteMills = board.countMills(NodeType.WHITE);

            if (blackMills>0)
            {
                System.out.println("mlynek dla czarnucha");
                playerTurn = NodeType.BLACK;
            }
            if (whiteMills>0)
            {
                System.out.println("mlynek dla białasa");
                playerTurn = NodeType.WHITE;
            }
            wasMillInPreviousTurn = blackMills>0 || whiteMills>0;






        }


    }

    public boolean wasNodeHereInPreviousMove(NodeType nodeType, int index) {
        Move previousMove;
        if (nodeType == NodeType.BLACK) {
            previousMove = lastNonCapturingMOveForBlack;
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
            lastIndex = ((ChangePieceLocationMove)previousMove).getToNodeIndex();
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
                lastNonCapturingMOveForBlack = move;
            }
        }

    }

    private Phase setGamePhase()
    {
        if (isMovingFreelyForPlayer(playerTurn))
        {
            gamePhase = Phase.PLACIING;
        }
        else if (isSlidingPhase())
        {
            gamePhase = Phase.SLIDING;
        }
        else
        {
            gamePhase = Phase.PLACIING;
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
        return board.getNumberOfBlackPiecesToBePlaced()==0 && board.getNumberOfWhitePiecesToBePlaced()==0;
    }

    private void switchPlayerTurn()
    {
        playerTurn = Utils.getOppositeNodeType(playerTurn);
    }

    private boolean someoneWon() {
        return false;
    }
}
