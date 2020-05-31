package battleship;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MenuController {

    /**
     * Ships to be placed on the field
     */
    private Ship[] randomShips = new Ship[]{new Battleship(), new Cruiser(), new Cruiser(), new Destroyer(),
            new Destroyer(), new Destroyer(), new Submarine(), new Submarine(), new Submarine(), new Submarine()};

    /**
     * {@link #randomShips} index of ship
     */
    private int index;

    /**
     * User name
     */
    private String myName;

    /**
     * Partner name
     */
    private String partnerName;

    /**
     * Is next placed ship will be horizontal
     */
    private boolean horizontal;

    /**
     * Is Server App
     */
    private boolean isServer;

    /**
     * ServerSocket to set the {@link #socket}
     */
    private ServerSocket server;

    /**
     * Socket we use in server-client dialog
     */
    private Socket socket;

    /**
     * Incoming messages from socket
     */
    private BufferedReader in;

    /**
     * Sending messages with socket
     */
    private BufferedWriter out;

    /**
     * Ocean, where we set the ships
     */
    Ocean ocean;

    /**
     * GridPane with buttons corresponding
     */
    @FXML
    GridPane gameField;

    /**
     * Buttons, represent {@link #gameField} buttons
     */
    Button[][] fieldButtons;

    /**
     * Button Start / Play / Connect
     */
    @FXML
    Button playButton;

    /**
     * Button. Shows info dialog on click
     */
    @FXML
    Button infoButton;

    /**
     * Button. Exits app on click
     */
    @FXML
    Button exitButton;

    /**
     * User name
     */
    @FXML
    TextField name;

    /**
     * Wanted server's name
     */
    @FXML
    TextField serverName;

    /**
     * Wanted server's port
     */
    @FXML
    TextField port;

    /**
     * Label with info of server name
     */
    @FXML
    Label serverNameLabel;

    /**
     * Show modal dialog
     *
     * @param info information text in dialog
     */
    private void showAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.showAndWait();
    }

    /**
     * Set on {@link #playButton}. Creates sockets, launches game
     */
    private class ClickOnPlayHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if (((Button) e.getSource()).getText().equals("Play")) {
                if (index > 9) {
                    Parent root = null;
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
                        if (isServer)
                            loader.setControllerFactory(c ->
                                    new GameController(ocean, isServer, socket, server, out, in, myName, partnerName));
                        else
                            loader.setControllerFactory(c ->
                                    new GameController(ocean, isServer, socket, out, in, myName, partnerName));
                        root = loader.load();
                        Stage infoStage = new Stage();
                        infoStage.setResizable(false);
                        infoStage.setTitle("Information");
                        infoStage.initOwner(((Button) e.getTarget()).getScene().getWindow());
                        infoStage.initModality(Modality.APPLICATION_MODAL);
                        infoStage.getIcons().add(new Image("file:../../resources/icon.png"));
                        infoStage.setScene(new Scene(root));
                        infoStage.show();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else {
                    showAlert("Place all of your ships first");
                }
            } else {
                if (name.getText().length() < 1 || port.getText().length() < 1) {
                    showAlert("Enter your name and number of the wanted port first");
                    return;
                }
                if (isServer) {
                    myName = name.getText();
                    //server app
                    try {
                        server = new ServerSocket(Integer.parseInt(port.getText()), 1);
                        socket = server.accept();
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        partnerName = in.readLine();
                        showAlert("Your game partner is " + partnerName);
                        out.write(name.getText());
                        out.flush();
                    } catch (Exception ex) {
                        try {
                            server.close();
                            socket.close();
                            in.close();
                            out.close();
                        } catch (Exception ignored) {
                        }
                        showAlert("Cannot start new game with this port");
                        return;
                    }
                } else {
                    //client app
                    try {
                        socket = new Socket("localhost", Integer.parseInt(port.getText()));
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        out.write(name.getText());
                        out.flush();
                        partnerName = in.readLine();
                        showAlert("Your game partner is " + partnerName);
                    } catch (Exception ex) {
                        try {
                            socket.close();
                            in.close();
                            out.close();
                        } catch (Exception ignored) {
                        }
                        showAlert("Cannot connect to the game with this port\n" +
                                "Maybe it's already busy");
                        return;
                    }
                }
                playButton.setText("Play");
            }
        }
    }

    /**
     * Shows info dialog
     */
    private class ClickOnInfoHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            String s = "- Enter your and server name in the corresponding text fields\n" +
                    "- Enter server port in text field Server port\n" +
                    (isServer ? "- Press Start to create new game\n" :
                            "- Press Connect to connect to the wanted server\n") +
                    "- During that you can place your ships at the field atop\n" +
                    "- Press key H to make ship lay horizontally or V to lay vertically,\n" +
                    "C to clear the ships arrangement, use your mouse or arrows to put the ship at the wanted place\n" +
                    "- There are 10 ships: 1 is 4 cell long, 2 of them are 3 cell long, " +
                    "3 - 2 cell long and 4 - 1 cell\n- You will place them in this order\n" +
                    "- Press Exit to close the app";
            showAlert(s);
        }
    }

    /**
     * Handler for click on the {@link #gameField}. Needed for placing the ships
     */
    private class ClickOnGameFieldHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if (index > 9) {
                playButton.setDisable(false);
                return;
            }
            Button clicked = (Button) e.getSource();
            int x = GridPane.getRowIndex(clicked);
            int y = GridPane.getColumnIndex(clicked);
            if (randomShips[index].okToPlaceShipAt(x, y, horizontal, ocean)) {
                randomShips[index].placeShipAt(x, y, horizontal, ocean);
                if (horizontal) {
                    for (int i = y; i < y + randomShips[index].getLength(); ++i)
                        fieldButtons[x][i].setStyle("-fx-base: red");
                } else {
                    for (int i = x; i < x + randomShips[index].getLength(); ++i)
                        fieldButtons[i][y].setStyle("-fx-base: red");
                }
                index++;
            } else {
                showAlert("This ship doesn't fit here");
            }
        }
    }

    /**
     * Initialize {@link #gameField} and its buttons and info.
     */
    private void initGameField() {
        fieldButtons = new Button[gameField.getRowCount() - 1][gameField.getColumnCount() - 1];
        for (int i = 0; i < gameField.getRowCount() - 1; ++i)
            for (int j = 0; j < gameField.getColumnCount() - 1; ++j) {
                Button field = new Button();
                field.setMinSize(10, 10);
                field.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                field.setPrefSize(100, 100);
                field.setOnAction(new ClickOnGameFieldHandler());
                field.setStyle("-fx-base: aqua");
                gameField.add(field, j, i);
                fieldButtons[i][j] = field;
            }
        for (int i = 0; i < gameField.getRowCount() - 1; ++i) {
            Label label = new Label(Integer.toString(i));
            label.setMinSize(10, 10);
            label.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            label.setPrefSize(100, 100);
            label.setAlignment(Pos.CENTER);
            gameField.add(label, gameField.getColumnCount() - 1, i);
        }
        for (int j = 0; j < gameField.getColumnCount() - 1; ++j) {
            Label label = new Label(Integer.toString(j));
            label.setMinSize(10, 10);
            label.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            label.setPrefSize(100, 100);
            label.setAlignment(Pos.CENTER);
            gameField.add(label, j, gameField.getRowCount() - 1);
        }
    }

    /**
     * Controller initialize, sets needed handlers and creates new {@link #ocean} object
     */
    @FXML
    void initialize() {
        if (!isServer) {
            playButton.setText("Connect");
        } else {
            serverName.setVisible(false);
            serverNameLabel.setVisible(false);
        }
        index = 0;
        horizontal = true;
        ocean = new Ocean();
        initGameField();
        infoButton.setOnAction(new ClickOnInfoHandler());
        exitButton.setOnAction(even -> Platform.exit());
        playButton.setOnAction(new ClickOnPlayHandler());
    }

    /**
     * Constructor for the class
     *
     * @param isServer if app is for Server or Client
     */
    public MenuController(boolean isServer) {
        this.isServer = isServer;
    }

    /**
     * Resets {@link #gameField} and {@link #ocean}
     *
     * @param random
     */
    private void clearField(boolean random) {
        horizontal = true;
        index = 0;
        ocean = new Ocean();
        if (random)
            ocean.placeAllShipsRandomly();
        for (int i = 0; i < gameField.getRowCount() - 1; ++i)
            for (int j = 0; j < gameField.getColumnCount() - 1; ++j) {
                if (ocean.getShip(i, j).getShipType().length() > 0)
                    fieldButtons[i][j].setStyle("-fx-base: red");
                else
                    fieldButtons[i][j].setStyle("-fx-base: aqua");
            }
    }

    /**
     * Handler of on Keyboard Press Key Event
     *
     * @param keyEvent
     */
    public void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case H:
                // Makes ship horizontal
                horizontal = true;
                break;
            case V:
                // Makes ship vertical
                horizontal = false;
                break;
            case C:
                // Clears gameField
                clearField(false);
                break;
            case R:
                // Places ships randomly
                clearField(true);
        }
    }
}
