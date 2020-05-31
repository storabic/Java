package battleship;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class InfoController {
    /**
     * Button, representing ocean(no ship)
     */
    @FXML
    Button oceanButton;

    /**
     * Button, representing hit ship
     */
    @FXML
    Button hitButton;

    /**
     * Button, representing sunk ship
     */
    @FXML
    Button sunkButton;

    /**
     * Label, representing ocean(no ship)
     */
    @FXML
    Label oceanLabel;

    /**
     * Label, representing hit ship
     */
    @FXML
    Label hitLabel;

    /**
     * Label, representing sunk ship
     */
    @FXML
    Label sunkLabel;

    @FXML
    void initialize() {
        oceanButton.setStyle("-fx-background-color: aqua");
        oceanLabel.setText("Ocean cell, there is no ship");
        hitButton.setStyle("-fx-background-color: red");
        hitLabel.setText("There lies the hit part of ship, but it's not sunk");
        sunkButton.setStyle("-fx-border-width: 3 3 3 3; -fx-border-color: black; -fx-background-color: red");
        sunkLabel.setText("This ship is sunk");
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        ((Stage) ((Button) keyEvent.getTarget()).getScene().getWindow()).close();
    }
}
