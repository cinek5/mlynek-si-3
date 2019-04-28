package logic.controller;

import logic.Board;
import logic.NodeType;
import logic.Phase;
import logic.moves.Move;
import logic.moves.MovingMove;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Cinek on 24.04.2019.
 */
public class GameController {
    private Queue<MovingMove> movesHistory;
    private PlayerTurn playerTurn;
    private Phase gamePhase;
    private boolean wasMillInPreviousTurn;
    private Board board;

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

    public GameController(PlayerTurn firstPlayerTurn, GameInput whiteInput, GameInput blackInput) {
        this.movesHistory = new ArrayDeque<>();
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
                if (playerTurn == PlayerTurn.WHITE) {
                    nextMove = whiteInput.getMove(this);

                } else {
                    nextMove = blackInput.getMove(this);
                }
                if (nextMove.isLegal(board, playerTurn))
                {
                    validMove = true;
                }
            }

            board.makeMove(nextMove);



            switchPlayerTurn();



            int blackMills = board.countMills(NodeType.BLACK);
            int whiteMills = board.countMills(NodeType.WHITE);

            if (blackMills>0)
            {
                System.out.println("mlynek dla czarnucha");
                playerTurn = PlayerTurn.BLACK;
            }
            if (whiteMills>0)
            {
                System.out.println("mlynek dla białasa");
                playerTurn = PlayerTurn.WHITE;
            }
            wasMillInPreviousTurn = blackMills>0 || whiteMills>0;






        }


    }

    private void setGamePhase()
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
    }
    private boolean isMovingFreelyForPlayer(PlayerTurn playerTurn)
    {
        if (playerTurn == PlayerTurn.WHITE)
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
        if (playerTurn == PlayerTurn.WHITE)
        {
            playerTurn = PlayerTurn.BLACK;
        }
        else
        {
            playerTurn = PlayerTurn.WHITE;
        }
    }

    private boolean someoneWon() {
        return false;
    }
}
