package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import logic.NodeType;
import logic.ai.AlphaBetaComputerAI;
import logic.ai.MinMaxComputerAI;
import logic.controller.ComputerPlayerInput;
import logic.controller.GameController;
import logic.controller.GameInput;
import logic.controller.PlayerInput;
import logic.evaluation.SecondGameStateEvaluator;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class StartController {

    private static final String MIN_MAX = "MIN-MAX";
    private static final String ALPHA_BETA = "ALPHA-BETA";
    private static final int PLAYER_HUMAN = 0;
    private static final int PLAYER_AI_MIN_MAX = 1;
    private static final int PLAYER_AI_ALPHA_BETA = 2;

    //first player
    @FXML
    private CheckBox firstPlayerAICheckBox;
    @FXML private ComboBox<String> firstPlayerAlgorithmComboBox;
    @FXML private TextField p1F1closedMill;
    @FXML private TextField p1F1mills;
    @FXML private TextField p1F1blockedPieces;
    @FXML private TextField p1F1pieces;
    @FXML private TextField p1F1twoPieces;
    @FXML private TextField p1F1threePieces;

    @FXML private TextField p1F2closedMill;
    @FXML private TextField p1F2mills;
    @FXML private TextField p1F2blockedPieces;
    @FXML private TextField p1F2pieces;
    @FXML private TextField p1F2winSetup;
    @FXML private TextField p1F2twoPieces;

    @FXML private TextField p1F3closedMill;
    @FXML private TextField p1F3twoPieces;
    @FXML private TextField p1F3threePieces;
    @FXML private TextField p1F3winSetup;
    private ArrayList<TextField> p1F1Fields;
    private ArrayList<TextField> p1F2Fields;
    private ArrayList<TextField> p1F3Fields;

    //second player
    @FXML private CheckBox secondPlayerAICheckBox;
    @FXML private ComboBox<String> secondPlayerAlgorithmComboBox;
    @FXML private TextField p2F1closedMill;
    @FXML private TextField p2F1mills;
    @FXML private TextField p2F1blockedPieces;
    @FXML private TextField p2F1pieces;
    @FXML private TextField p2F1twoPieces;
    @FXML private TextField p2F1threePieces;

    @FXML private TextField p2F2closedMill;
    @FXML private TextField p2F2mills;
    @FXML private TextField p2F2blockedPieces;
    @FXML private TextField p2F2pieces;
    @FXML private TextField p2F2winSetup;
    @FXML private TextField p2F2twoPieces;

    @FXML private TextField p2F3closedMill;
    @FXML private TextField p2F3twoPieces;
    @FXML private TextField p2F3threePieces;
    @FXML private TextField p2F3winSetup;
    private ArrayList<TextField> p2F1Fields;
    private ArrayList<TextField> p2F2Fields;
    private ArrayList<TextField> p2F3Fields;

    //general
    @FXML private CheckBox logHistoryCheckBox;
    @FXML private CheckBox heuristicsCheckBox;

    @FXML private TextField p1depth;
    @FXML private TextField p2depth;


    @FXML
    private void initialize() {
        initializeAlgorithmTypes();
        addTextFieldsToArrays();
        setDefaultValues();
        setTextFormatters();
        disableTextFields();
        setListenersOnAICheckBoxes();
    }

    public void onStartClick(ActionEvent actionEvent) {
        int firstPlayerMode = firstPlayerAICheckBox.isSelected() ? (
                firstPlayerAlgorithmComboBox.getValue().equals(
                        MIN_MAX) ? PLAYER_AI_MIN_MAX:
                        (firstPlayerAlgorithmComboBox.getValue().equals(ALPHA_BETA) ? PLAYER_AI_ALPHA_BETA
                                : -1)
        ) : PLAYER_HUMAN;
        int secondPlayerMode = secondPlayerAICheckBox.isSelected() ? (
                secondPlayerAlgorithmComboBox.getValue().equals(MIN_MAX) ? PLAYER_AI_MIN_MAX :
                        (secondPlayerAlgorithmComboBox.getValue().equals(ALPHA_BETA) ? PLAYER_AI_ALPHA_BETA
                                : -1)
        ) : PLAYER_HUMAN;

        GameInput whiteInput = createPlayer(firstPlayerMode, NodeType.WHITE, true, heuristicsCheckBox.isSelected());
        GameInput blackInput = createPlayer(secondPlayerMode, NodeType.BLACK, false, heuristicsCheckBox.isSelected());
        GameController gameController = new GameController(NodeType.BLACK, whiteInput, blackInput);
        gameController.setDisplayMoves(logHistoryCheckBox.isSelected());
        NodeType winner =  gameController.game();

        System.out.println("Wyniki badan: ");
        System.out.println("Liczba ruchów gracza wygrywajacego "+ (winner==NodeType.BLACK? gameController.getNumberOfMovesBlack() : gameController.getNumberOfMovesWhite()));
        System.out.println("Czas przetwarzania dla gracza wygrywajacego "+ (winner==NodeType.BLACK? gameController.getComputingTimeInMsBlack() : gameController.getComputingTimeInMsWhite()));

        if (winner==NodeType.BLACK)
        {
            if (blackInput instanceof ComputerPlayerInput)
            {
                ComputerPlayerInput comp = (ComputerPlayerInput) blackInput;
                System.out.println("Nodes visited: "+comp.getNodesVisited());
            }
        }
        else
        {
            if (whiteInput instanceof ComputerPlayerInput)
            {
                ComputerPlayerInput comp = (ComputerPlayerInput) whiteInput;
                System.out.println("Nodes visited: "+comp.getNodesVisited());
            }
        }
    }

    private GameInput createPlayer(int playerMode, NodeType nodeType, boolean isFirstPlayer, boolean isHeuristics)
    {
        int depth = Integer.parseInt(isFirstPlayer? p1depth.getText() : p2depth.getText())-1;
        if (playerMode==PLAYER_HUMAN)
        {
            return new PlayerInput(nodeType);
        }
        if (playerMode==PLAYER_AI_MIN_MAX)
        {
            return new ComputerPlayerInput(nodeType, new MinMaxComputerAI(new SecondGameStateEvaluator(getWeights(isFirstPlayer)),nodeType, depth, isHeuristics));
        }
        else
        {
            return new ComputerPlayerInput(nodeType, new AlphaBetaComputerAI(new SecondGameStateEvaluator(getWeights(isFirstPlayer)),nodeType, depth, isHeuristics));
        }
    }

    private void initializeAlgorithmTypes() {
        ArrayList<String> algorithmTypes = new ArrayList<String>(2);
        algorithmTypes.add(MIN_MAX);
        algorithmTypes.add(ALPHA_BETA);

        firstPlayerAlgorithmComboBox.setItems(FXCollections.observableArrayList(algorithmTypes));
        firstPlayerAlgorithmComboBox.setValue(algorithmTypes.get(0));
        firstPlayerAlgorithmComboBox.setDisable(true);

        secondPlayerAlgorithmComboBox.setItems(FXCollections.observableArrayList(algorithmTypes));
        secondPlayerAlgorithmComboBox.setValue(algorithmTypes.get(0));
        secondPlayerAlgorithmComboBox.setDisable(true);
    }

    private void setListenersOnAICheckBoxes() {
        firstPlayerAICheckBox.setOnAction((event -> {
            firstPlayerAlgorithmComboBox.setDisable(!firstPlayerAICheckBox.isSelected());
            setListenerToTextFields(p1F1Fields, firstPlayerAICheckBox);
            setListenerToTextFields(p1F2Fields, firstPlayerAICheckBox);
            setListenerToTextFields(p1F3Fields, firstPlayerAICheckBox);
        }));
        secondPlayerAICheckBox.setOnAction((event -> {
            secondPlayerAlgorithmComboBox.setDisable(!secondPlayerAICheckBox.isSelected());
            setListenerToTextFields(p2F1Fields, secondPlayerAICheckBox);
            setListenerToTextFields(p2F2Fields, secondPlayerAICheckBox);
            setListenerToTextFields(p2F3Fields, secondPlayerAICheckBox);
        }));
    }

    private void setTextFormatters() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
    }

    private void addTextFieldsToArrays() {
        p1F1Fields = new ArrayList<>(6);
        p1F1Fields.add(p1F1closedMill);
        p1F1Fields.add(p1F1mills);
        p1F1Fields.add(p1F1blockedPieces);
        p1F1Fields.add(p1F1pieces);
        p1F1Fields.add(p1F1twoPieces);
        p1F1Fields.add(p1F1threePieces);

        p1F2Fields = new ArrayList<>(7);
        p1F2Fields.add(p1F2closedMill);
        p1F2Fields.add(p1F2mills);
        p1F2Fields.add(p1F2blockedPieces);
        p1F2Fields.add(p1F2pieces);
        p1F2Fields.add(p1F2winSetup);
        p1F2Fields.add(p1F2twoPieces);

        p1F3Fields = new ArrayList<>(4);
        p1F3Fields.add(p1F3closedMill);
        p1F3Fields.add(p1F3twoPieces);
        p1F3Fields.add(p1F3threePieces);
        p1F3Fields.add(p1F3winSetup);


        p2F1Fields = new ArrayList<>(6);
        p2F1Fields.add(p2F1closedMill);
        p2F1Fields.add(p2F1mills);
        p2F1Fields.add(p2F1blockedPieces);
        p2F1Fields.add(p2F1pieces);
        p2F1Fields.add(p2F1twoPieces);
        p2F1Fields.add(p2F1threePieces);

        p2F2Fields = new ArrayList<>(7);
        p2F2Fields.add(p2F2closedMill);
        p2F2Fields.add(p2F2mills);
        p2F2Fields.add(p2F2blockedPieces);
        p2F2Fields.add(p2F2pieces);
        p2F2Fields.add(p2F2winSetup);
        p2F2Fields.add(p2F2twoPieces);

        p2F3Fields = new ArrayList<>(4);
        p2F3Fields.add(p2F3closedMill);
        p2F3Fields.add(p2F3twoPieces);
        p2F3Fields.add(p2F3threePieces);
        p2F3Fields.add(p2F3winSetup);
    }

    private void setListenerToTextFields(ArrayList<TextField> list, CheckBox checkBox) {
        for (TextField textField : list)
            textField.setDisable(!checkBox.isSelected());
    }

    private void setDefaultValues() {
        //Player 1
        p1F1closedMill.setText("400");
        p1F1mills.setText("40");
        p1F1blockedPieces.setText("1");
        p1F1pieces.setText("9");
        p1F1twoPieces.setText("12");
        p1F1threePieces.setText("7");

        p1F2closedMill.setText("14");
        p1F2mills.setText("43");
        p1F2blockedPieces.setText("10");
        p1F2pieces.setText("11");
        p1F2winSetup.setText("1086");
        p1F2twoPieces.setText("7");

        p1F3twoPieces.setText("10");
        p1F3threePieces.setText("1");
        p1F3closedMill.setText("20");
        p1F3winSetup.setText("1190");


        //Player 2
        p2F1closedMill.setText("400");
        p2F1mills.setText("40");
        p2F1blockedPieces.setText("1");
        p2F1pieces.setText("9");
        p2F1twoPieces.setText("12");
        p2F1threePieces.setText("7");

        p2F2closedMill.setText("14");
        p2F2mills.setText("43");
        p2F2blockedPieces.setText("10");
        p2F2pieces.setText("11");
        p2F2winSetup.setText("1086");
        p2F2twoPieces.setText("7");

        p2F3twoPieces.setText("10");
        p2F3threePieces.setText("1");
        p2F3closedMill.setText("20");
        p2F3winSetup.setText("1190");

    }

    private ArrayList<ArrayList<Integer>> getWeights(boolean isFirstPlayer) {
        ArrayList<Integer> f1Weights = new ArrayList<>(6);
        getIntegerValues(isFirstPlayer ? p1F1Fields : p2F1Fields, f1Weights);

        ArrayList<Integer> f2Weights = new ArrayList<>(7);
        getIntegerValues(isFirstPlayer ? p1F2Fields : p2F2Fields, f2Weights);

        ArrayList<Integer> f3Weights = new ArrayList<>(4);
        getIntegerValues(isFirstPlayer ? p1F3Fields : p2F3Fields, f3Weights);

        ArrayList<ArrayList<Integer>> result = new ArrayList<>(3);
        result.add(f1Weights);
        result.add(f2Weights);
        result.add(f3Weights);

        return result;
    }

    private void getIntegerValues(ArrayList<TextField> source, ArrayList<Integer> destination) {
        for (TextField textField : source)
            destination.add(Integer.parseInt(textField.getText()));
    }

    private void disableTextFields() {
        for (TextField textField : p1F1Fields)
            textField.setDisable(true);
        for (TextField textField : p1F2Fields)
            textField.setDisable(true);
        for (TextField textField : p1F3Fields)
            textField.setDisable(true);

        for (TextField textField : p2F1Fields)
            textField.setDisable(true);
        for (TextField textField : p2F2Fields)
            textField.setDisable(true);
        for (TextField textField : p2F3Fields)
            textField.setDisable(true);
    }


}
