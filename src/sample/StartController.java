package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController {

    @FXML private Button startButton;

    public void onStartClick(ActionEvent actionEvent) {
        GuiGameController board = new GuiGameController();
        board.showGame();
    }
}
