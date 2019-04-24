package sample;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Board;
import logic.Position;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Cinek on 24.04.2019.
 */
public class GuiGameController {

    private Stage stage;
    private BorderPane pane;
    private ImageView boardImage;
    private Board board;
    private ImageView actualPlayer;

    private ArrayList<Circle> whitePieces;
    private ArrayList<Circle> blackPieces;

    private static final int EMPTY_PIECE_SIZE = 45;
    private static final int PIECE_SIZE = 55;
    private static final int NUMBER_OF_PIECES = 9;

    public GuiGameController() {
        stage = new Stage();
        pane = new BorderPane();
        board = new Board();
        stage.setScene(new Scene(pane, 800, 900));
        stage.setTitle("Gramy");

        whitePieces = new ArrayList<>(NUMBER_OF_PIECES);
        blackPieces = new ArrayList<>(NUMBER_OF_PIECES);

        actualPlayer = null;

       //Position.generateAllPositions();
    }

    private void prepareMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        pane.setTop(menuBar);

        Menu menu = new Menu("Opcje");
        MenuItem exit = new MenuItem("Zakoncz");
        exit.setOnAction(actionEvent -> stage.close());
        menu.getItems().add(exit);

        menuBar.getMenus().add(menu);
    }

//    private void prepareUnusedPieces() {
//        int fX = 130;
//        int fY = 130;
//        int playersDifference = 390;
//        int distance = 80;
//        for (int j = 0; j < 3; j++) {
//            for (int i = 0; i < 3; i++) {
//                drawPieceAtPosition(false, fX + i * distance, fY + j * distance, Piece.WHITE);
//                drawPieceAtPosition(false, fX + i * distance, fY + playersDifference + j * distance, Piece.BLACK);
//            }
//        }
//    }

    private void prepareEmptyBoard() {
        File emptyBoardImage = new File("src/Layout/board.png");
        Image image = new Image(emptyBoardImage.toURI().toString());
        boardImage = new ImageView();
        boardImage.setImage(image);
        boardImage.setPreserveRatio(true);
        boardImage.fitWidthProperty().bind(stage.widthProperty());
        boardImage.fitHeightProperty().bind(stage.heightProperty());

        pane.setCenter(boardImage);
        //board.initializeEmptyBoard();
    }

//    private void markActualPlayer(Piece addedPiece) {
//        if (actualPlayer == null) {
//            File diceFile = new File("src/Layout/dice.png");
//            Image image = new Image(diceFile.toURI().toString());
//            actualPlayer = new ImageView();
//            actualPlayer.setImage(image);
//            actualPlayer.setPreserveRatio(true);
//            pane.getChildren().add(actualPlayer);
//            actualPlayer.setX((double) 30);
//            actualPlayer.setY((double) 50);
//        }
//        else {
//            int y;
//            if (addedPiece.equals(Piece.BLACK))
//                y = 50;
//            else
//                y = 440;
//            actualPlayer.setY((double) y);
//        }
//    }

//    private void detectClickOnBoard() {
//        boardImage.setOnMouseClicked(e -> {
//            Position p = getClosestPosition((int)e.getX(), (int)e.getY());
//            if (p != null) {
//                Piece addedPiece = board.addPieceAtPosition(p);
//                if (!addedPiece.equals(Piece.NONE))
//                    drawPieceAtPosition(true, p.getPositionX(), p.getPositionY(), addedPiece);
//            }
//            else
//                System.out.println("Poza obszarem");
//        });
//    }

//    private Position getClosestPosition(int x, int y) {
//        int temp;
//        int foundX = 0;
//        int foundY = 0;
//        int distX = Integer.MAX_VALUE;
//        int distY = Integer.MAX_VALUE;
//        for (int i = 0; i < Position.firstXPositions.length; i++) {
//            for (int d = 0; d < 3; d++) {
//                temp = Position.firstXPositions[i] + d * Position.differences[i];
//                if (Math.abs(x - temp) < distX && Math.abs(x - temp) < EMPTY_PIECE_SIZE/2) {
//                    foundX = temp;
//                    distX = Math.abs(x - temp);
//                }
//                temp = Position.firstYPositions[i] + d * Position.differences[i];
//                if (Math.abs(y - temp) < distY && Math.abs(y - temp) < EMPTY_PIECE_SIZE/2) {
//                    foundY = temp;
//                    distY = Math.abs(y - temp);
//                }
//            }
//        }
//        if (distX != Integer.MAX_VALUE && distY != Integer.MAX_VALUE)
//            return new Position(foundX, foundY);
//        else
//            return null;
//    }

    public void showGame() {
        prepareMenuBar();
        prepareEmptyBoard();
        //prepareUnusedPieces();
        //markActualPlayer(Piece.NONE);
        //detectClickOnBoard();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

//    private void drawPieceAtPosition(boolean detectedByClick, int x, int y, Piece piece) {
//        y += 40;
//        Circle circle = null;
//        if ((piece.equals(Piece.WHITE) && !detectedByClick) ||
//                (piece.equals(Piece.BLACK) && !detectedByClick)) {
//            circle = new Circle();
//            circle.setRadius(PIECE_SIZE / 2);
//            circle.setFill(piece.equals(Piece.WHITE) ? Color.WHITE : Color.BLACK);
//            pane.getChildren().add(circle);
//            if (piece.equals(Piece.WHITE))
//                whitePieces.add(circle);
//            else
//                blackPieces.add(circle);
//        }
//        else {
//            if (piece.equals(Piece.WHITE) && whitePieces.size() != 0) {
//                circle = whitePieces.get(whitePieces.size()-1);
//                whitePieces.remove(whitePieces.size()-1);
//            }
//            else if (piece.equals(Piece.BLACK) && blackPieces.size() != 0) {
//                circle = blackPieces.get(blackPieces.size()-1);
//                blackPieces.remove(blackPieces.size()-1);
//            }
//        }
//        if (circle != null) {
//            circle.setCenterX((double) x);
//            circle.setCenterY((double) y);
//            if (detectedByClick)
//                markActualPlayer(piece);
//        }
//    }

}