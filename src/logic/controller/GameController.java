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

    private boolean displayMoves=true;

    private NodeType winner = NodeType.NONE;

    private int numberOfMovesWhite = 0;
    private int numberOfMovesBlack = 0;

    private int computingTimeInMsBlack = 0;
    private int computingTimeInMsWhite = 0;

    public int getComputingTimeInMsBlack() {
        return computingTimeInMsBlack;
    }


    public int getComputingTimeInMsWhite() {
        return computingTimeInMsWhite;
    }


    public Move getLastNonCapturingMoveForWhite() {
        return lastNonCapturingMoveForWhite;
    }



    public Move getLastNonCapturingMoveForBlack() {
        return lastNonCapturingMoveForBlack;
    }

    public int getNumberOfMovesWhite() {
        return numberOfMovesWhite;
    }

    public int getNumberOfMovesBlack() {
        return numberOfMovesBlack;
    }

    public Queue<Move> getMovesHistoryWhite() {
        return movesHistoryWhite;
    }

    public Queue<Move> getMovesHistoryBlack() {
        return movesHistoryBlack;
    }

    public NodeType getPlayerTurn() {
        return playerTurn;
    }

    public boolean isWasMillInPreviousTurn() {
        return wasMillInPreviousTurn;
    }

    public void setDisplayMoves(boolean displayMoves)
    {
        this.displayMoves = displayMoves;
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


    public NodeType game() {
        while (!someoneWon() && playSingleTurn());





        board.printBoard();

        return winner;

    }

    public boolean playSingleTurn()
    {
        if (displayMoves) {
            board.printBoard();
        }

        setGamePhase();
        if (displayMoves) {
            if (!wasMillInPreviousTurn) {
                System.out.println(gamePhase);
            } else {
                System.out.println("CAPTURING ");
            }
        }

        boolean validMove = false;
        Move nextMove = null;

        if( displayMoves ) {
            System.out.println("Ruch gracza: " + playerTurn);
        }
        GameInput currentInput;
        while(!validMove) {
            if (playerTurn == NodeType.WHITE) {
                currentInput = whiteInput;
            } else {
                currentInput = blackInput;
            }

            long start = System.nanoTime();

            nextMove = currentInput.getMove(this);

            long end = System.nanoTime();

            if (currentInput instanceof  PlayerInput)
            {
                if (nextMove.isLegal(this, playerTurn))
                {
                    validMove = true;
                }

            }


            else
            {
                if (playerTurn==NodeType.BLACK)
                {
                    numberOfMovesBlack++;
                    computingTimeInMsBlack+=((start-end)/1000);
                }
                else
                {
                    numberOfMovesWhite++;
                    computingTimeInMsWhite+=((start-end)/1000);
                }
                validMove = true;
            }


        }

        if (nextMove==null)
        {
            System.out.println("Player "+playerTurn+" has been blocked. "+Utils.getOppositeNodeType(playerTurn)+" has won");
            winner = Utils.getOppositeNodeType(playerTurn);
            return false;
        }
        nextMove.makeMove(board);


        saveMove(nextMove);



        int mills = board.countMills(playerTurn);

        if (mills>0 && displayMoves)
        {
            System.out.println("MLYNEK: "+mills);
        }
        else
        {
            switchPlayerTurn();
        }


        wasMillInPreviousTurn = mills>0;

        return true;


    }

    public GameStateHelper playMove(Move move)
    {

        GameStateHelper gameStateHelper = new GameStateHelper(playerTurn, wasMillInPreviousTurn,
                lastNonCapturingMoveForWhite, lastNonCapturingMoveForBlack);

        setGamePhase();

        move.makeMove(board);


        int mills = board.countMills(playerTurn);

        if (mills==0)
        {
            switchPlayerTurn();
        }



        wasMillInPreviousTurn = mills>0;


        return gameStateHelper;



    }

    public void undoMove(Move move, GameStateHelper stateBeforeMoveWasMade)
    {
        move.undoMove(board);

        this.lastNonCapturingMoveForWhite = stateBeforeMoveWasMade.lastNonCapturingMoveForWhite;
        this.lastNonCapturingMoveForBlack = stateBeforeMoveWasMade.lastNonCapturingMoveForBlack;
        this.playerTurn = stateBeforeMoveWasMade.playerTurn;
        this.wasMillInPreviousTurn = stateBeforeMoveWasMade.wasMill;

    }


    public List<Move> generatePossibleMoves(NodeType playerNodeType)
    {
       Phase phase = getGamePhase(playerNodeType);

       if (isWasMillInPreviousTurn())
       {
           List<Move> moves = generatePossibleCapturingMoves(playerNodeType);
           if (!moves.isEmpty())
               return moves;
       }

       if (phase.equals(Phase.PLACING))
       {
           return generatePossiblePlacingMoves(playerNodeType);
       }

       if (phase.equals(Phase.SLIDING))
       {
           return generatePossibleSlidingMoves(playerNodeType);
       }

       if (phase.equals(Phase.MOVING_FREELY))
       {
           return generatePossibleMovingMoves(playerNodeType);
       }

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
                Move move = new CapturingMove(i, oppositeNodeType);
                if (move.isLegal(this,playerNodeType)) {
                    moves.add(move);
                }
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

                    if (!wasNodeHereInPreviousMove(playerNodeType, toIndex, fromIndex))
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
        List<Node>  nodes = board.getNodes();


        for (Node node: nodes)
        {
            if (node.getNodeType()!=playerNodeType)
            {
                continue;
            }

            for (Node nodeTo : nodes)
            {
                if (nodeTo.getNodeType()!=NodeType.NONE)
                {
                    continue;
                }
                int toIndex  = nodeTo.getIndex();
                int fromIndex = node.getIndex();
                if (!wasNodeHereInPreviousMove(playerNodeType, toIndex, fromIndex))
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

    public boolean wasNodeHereInPreviousMove(NodeType nodeType, int toIndex, int fromIndex) {
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
        int lastFromIndex = -1;
        int lastToIndex = -1;
        if (previousMove instanceof PlacingMove)
        {
            lastFromIndex = ((PlacingMove) previousMove).getNodeIndex();
            return lastFromIndex==toIndex;
        }
        if (previousMove instanceof ChangePieceLocationMove)
        {
            ChangePieceLocationMove prvMove = (ChangePieceLocationMove) previousMove;
            lastFromIndex = prvMove.getFromNodeIndex();
            lastToIndex = prvMove.getToNodeIndex();

            return lastFromIndex==toIndex && lastToIndex == fromIndex;
        }

        return false;


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
      gamePhase = getGamePhase(playerTurn);
      return gamePhase;
    }

    public Phase getGamePhase(NodeType nodeType)
    {
        Phase gamePhase=null;
        if (isMovingFreelyForPlayer(nodeType))
        {
            gamePhase = Phase.MOVING_FREELY;
        }
        else if (isSlidingPhaseFor(nodeType))
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
            return isSlidingPhase(playerTurn) && board.getNumberOfWhitePiecesOnBoard()<=3;
        }
        else
        {
            return isSlidingPhase(playerTurn) && board.getNumberOfBlackPiecesOnBoard()<=3;
        }
    }

    private boolean isSlidingPhase(NodeType playerTurn)
    {
       return isSlidingPhaseFor(playerTurn);
    }

    private boolean isSlidingPhaseFor(NodeType player)
    {
        if (player==NodeType.BLACK)
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
           winner = NodeType.WHITE;
           return true;
        }
        if (hasLostByPiecesCapture(NodeType.WHITE))
        {
            winner = NodeType.BLACK;
            System.out.println("BLACK HAS WON!!! White has too few pieces.");
            return true;
        }

        if (isBlocked(NodeType.BLACK))
        {
            winner =NodeType.WHITE;
            System.out.println("White has won! Black is blocked.");
            return true;
        }
        if (isBlocked(NodeType.WHITE))
        {
            winner = NodeType.BLACK;
            System.out.println("Black has won! White is blocked.");
            return true;
        }

        return false;
    }

    private boolean isBlocked(NodeType nodeType)
    {
        return board.isPlayerBlocked(nodeType);
    }


    public static class GameStateHelper
    {
        NodeType playerTurn;
        boolean wasMill;
        Move lastNonCapturingMoveForWhite;
        Move lastNonCapturingMoveForBlack;

        public GameStateHelper(NodeType playerTurn, boolean wasMill, Move lastNonCapturingMoveForWhite, Move lastNonCapturingMoveForBlack) {
            this.playerTurn = playerTurn;
            this.wasMill = wasMill;
            this.lastNonCapturingMoveForWhite = lastNonCapturingMoveForWhite;
            this.lastNonCapturingMoveForBlack = lastNonCapturingMoveForBlack;
        }
    }
}
