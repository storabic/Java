package battleship;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

/**
 * MVC Pattern Controller
 */
public class GameController {

    /**
     * User name
     */
    String name;

    /**
     * Partner name
     */
    String partnerName;

    /**
     * GridPane of partner ships
     */
    @FXML
    GridPane opponentGameField;

    /**
     * GridPane of user ships
     */
    @FXML
    GridPane myGameField;

    /**
     * Buttons of {@link #opponentGameField}
     */
    private Button[][] opponentButtons;

    /**
     * Buttons of {@link #myGameField}
     */
    private Button[][] myButtons;

    /**
     * Button, shows info dialog
     */
    @FXML
    Button buttonInfo;

    /**
     * Button, which will be released in the next version
     */
    @FXML
    Button buttonCancel;

    /**
     * X coordinate text input
     */
    @FXML
    TextField x;

    /**
     * Y coordinate text input
     */
    @FXML
    TextField y;

    /**
     * Assisting field for{@link #x}
     */
    private int coordX;

    /**
     * Assisting field for{@link #y}
     */
    private int coordY;

    /**
     * Contains info about shots done
     */
    @FXML
    Label shots;

    /**
     * Contains info about number of whole ships
     */
    @FXML
    Label whole;

    /**
     * Contains info about number of hit, but not sunk ships
     */
    @FXML
    Label hit;

    /**
     * Contains info about number of sunk ships
     */
    @FXML
    Label sunk;

    /**
     * Logging game info
     */
    @FXML
    TextArea log;

    /**
     * Game field
     */
    private Ocean myOcean;

    /**
     * Is user turn
     */
    private boolean myTurn;

    /**
     * Socket for client - server communication
     */
    private Socket socket;

    /**
     * Server Socket
     */
    private ServerSocket server;

    /**
     * Incoming messages
     */
    private BufferedReader in;

    /**
     * Sending messages
     */
    private BufferedWriter out;

    Task task = new Task<Void>() {
        String ans;

        @Override
        protected Void call() throws Exception {
            return null;
        }

        @Override
        public void run() {
            try {
                while ((ans = in.readLine()) == null) ;
                if (ans.equals("cancel")) {
                    showAlert(partnerName + ": Stop game! OK?");
                    ((Stage) buttonInfo.getScene().getWindow()).close();
                } else {
                    int i = ans.charAt(0) - '0';
                    int j = ans.charAt(1) - '0';
                    out.write(shoot(i, j));
                    out.flush();
                    myTurn = true;
                }
            } catch (IOException ignored) {
            }
        }
    };

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
     * Constructor for Client App
     *
     * @param ocean
     * @param isServer
     * @param socket
     * @param out
     * @param in
     */
    public GameController(Ocean ocean, boolean isServer, Socket socket,
                          BufferedWriter out, BufferedReader in, String name, String partnerName) {
        this.myOcean = ocean;
        this.myTurn = isServer;
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.name = name;
        this.partnerName = partnerName;

        //Start listening for Server game turn
        if (!isServer)
            new Thread(task).start();
    }

    /**
     * Constructor for server app
     *
     * @param ocean
     * @param isServer
     * @param socket
     * @param server
     * @param out
     * @param in
     */
    public GameController(Ocean ocean, boolean isServer, Socket socket, ServerSocket server,
                          BufferedWriter out, BufferedReader in, String name, String partnerName) {
        this(ocean, isServer, socket, out, in, name, partnerName);
        this.server = server;
    }

    /**
     * Updates containment of {@link #log}
     *
     * @param x   coordinate
     * @param y   coordinate
     * @param ans result of shot in (x,y)
     */
    private void updateLog(String id, int x, int y, String ans) {
        log.appendText(String.format(id + ": %d %d = ", x, y) + ans + "\n");
    }

    /**
     * Updates containment of {@link #log} by ans
     *
     * @param ans new line
     */
    private void updateLog(String ans) {
        log.appendText(ans + "\n");
    }

    /**
     * Blocks all control elements to prevent bad occasions
     */
    private void stopGame() {
        for (Button[] buttonsRow : opponentButtons)
            for (Button button : buttonsRow) {
                button.setDisable(true);
            }
        x.setDisable(true);
        y.setDisable(true);
    }

    /**
     * Update info elements
     */
    private void updateInfo() {
        shots.setText(Integer.toString(myOcean.getShotsFired()));
        whole.setText(Integer.toString(myOcean.getWholeCount()));
        hit.setText(Integer.toString(10 - myOcean.getWholeCount() - myOcean.getSunkCount()));
        sunk.setText(Integer.toString(myOcean.getSunkCount()));
    }

    /**
     * Draws borders for sunk ships
     *
     * @param sunkShip new sunk ship
     */
    private void drawShip(Ship sunkShip) {
        int i = sunkShip.getBowRow();
        int j = sunkShip.getBowColumn();
        if (sunkShip.getLength() == 1) {
            myButtons[i][j].setStyle("-fx-border-width: 3 3 3 3;" +
                    " -fx-border-color: black; -fx-background-color: red");
            return;
        }
        if (sunkShip.isHorizontal()) {
            myButtons[i][j].setStyle("-fx-border-width: 3 0 3 3;" +
                    " -fx-border-color: black; -fx-background-color: red");
            for (int k = j + 1; k < j + sunkShip.getLength() - 1; ++k) {
                myButtons[i][k].setStyle("-fx-border-width: 3 0 3 0;" +
                        " -fx-border-color: black; -fx-background-color: red");
            }
            myButtons[i][j + sunkShip.getLength() - 1].setStyle("-fx-border-width: 3 3 3 0;" +
                    " -fx-border-color: black; -fx-background-color: red");
        } else {
            myButtons[i][j].setStyle("-fx-border-width: 3 3 0 3;" +
                    " -fx-border-color: black; -fx-background-color: red");
            for (int k = i + 1; k < i + sunkShip.getLength() - 1; ++k) {
                myButtons[k][j].setStyle("-fx-border-width: 0 3 0 3;" +
                        " -fx-border-color: black; -fx-background-color: red");
            }
            myButtons[i + sunkShip.getLength() - 1][j].setStyle("-fx-border-width: 0 3 3 3;" +
                    " -fx-border-color: black; -fx-background-color: red");
        }
    }

    /**
     * Shoots in given coordinates
     *
     * @param i vertical coordinate
     * @param j horizontal coordinate
     */
    private String shoot(int i, int j) {
        String ans = "";
        if (myOcean.shootAt(i, j)) {
            if (myOcean.isSunk(i, j)) {
                Ship sunkShip = myOcean.getShip(i, j);
                updateLog(partnerName, i, j, "sunk " + sunkShip.getShipType());
                drawShip(sunkShip);
                if (myOcean.isGameOver()) {
                    stopGame();
                    updateLog("Game over!");
                    ans = "sunk " + sunkShip.getShipType() + " gameover";
                } else
                    ans = "sunk " + sunkShip.getShipType();
            } else {
                myButtons[i][j].setText("X");
                updateLog(partnerName, i, j, "hit");
                ans = "hit";
            }
        } else {
            myButtons[i][j].setText("X");
            updateLog(partnerName, i, j, "miss");
            ans = "miss";
        }
        updateInfo();
        return ans;
    }

    /**
     * Handler for event "Click on buttons of gamefield"
     * Shoots in clicked position.
     */
    private class ClickOnGameFieldHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if (!myTurn)
                return;
            Button clicked = (Button) e.getSource();
            if (!clicked.isFocusTraversable()) {
                log.appendText("You shot at that cell already\n");
                return;
            }
            int i = GridPane.getRowIndex(clicked);
            int j = GridPane.getColumnIndex(clicked);
            partnerShoot(i, j);
        }
    }

    /**
     * Shoot at partner's game field
     */
    private void partnerShoot(int i, int j) {
        try {
            out.write(String.format("%d%d", i, j));
            out.flush();
            String ans = in.readLine();
            if (ans.contains("gameover"))
                stopGame();
            updateLog(name, i, j, ans);
            new Thread(task).start();
        } catch (IOException ex) {
            showAlert("Problems with connection");
        }
    }

    /**
     * Handler for event "X or Y coordinates were inputted"
     * Shots in (X, Y) if X, Y are proper
     */
    private class CoordinatesChangedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if (!myTurn)
                return;
            TextField field = (TextField) e.getSource();
            if (field.getText().equals("ESC")) {
                buttonInfo.requestFocus();
                return;
            }
            if (field.getLength() == 1) {
                int num;
                try {
                    num = Integer.parseInt(field.getText());
                    if (field == x)
                        coordX = num;
                    else
                        coordY = num;
                    if (coordX != -1 && coordY != -1) {
                        if (!opponentButtons[coordX][coordY].isFocusTraversable()) {
                            log.appendText("You shot at that cell already\n");
                            return;
                        }
                        partnerShoot(coordY, coordX);
                        coordX = -1;
                        coordY = -1;
                        x.setText("");
                        y.setText("");
                    }
                    if (field == x)
                        y.requestFocus();
                    else
                        x.requestFocus();
                    return;
                } catch (NumberFormatException ignored) {
                }
            }
            field.setText("0-9");
        }
    }

    /**
     * Handler for event "Pressing button New Game"
     * Restarts the game
     */
    private class CancelHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            // TODO
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setHeaderText("This game will be lost");
            confirmationDialog.setTitle("Confirm");
            Optional<ButtonType> option = confirmationDialog.showAndWait();
            if (option.get() != ButtonType.OK)
                return;

            for (Button[] buttonsRow : opponentButtons)
                for (Button button : buttonsRow) {
                    button.setStyle(null);
                    button.setFocusTraversable(true);
                }
            x.setDisable(false);
            y.setDisable(false);
            log.clear();
            shots.setText("0");
            whole.setText("10");
            hit.setText("0");
            sunk.setText("0");
            coordX = -1;
            coordY = -1;
            myOcean = new Ocean();
            myOcean.placeAllShipsRandomly();
            updateLog("Welcome to BattleShip game");
        }
    }

    /**
     * Handler for click on {@link #buttonInfo}
     */
    private class ClickOnInfoHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("info.fxml"));
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
        }
    }

    /**
     * Initializes user {@link #myGameField} and partner {@link #opponentGameField} game fields
     */
    private void initGameFields() {
        opponentButtons = new Button[opponentGameField.getRowCount() - 1][opponentGameField.getColumnCount() - 1];
        myButtons = new Button[opponentGameField.getRowCount() - 1][opponentGameField.getColumnCount() - 1];
        for (int i = 0; i < opponentGameField.getRowCount() - 1; ++i)
            for (int j = 0; j < opponentGameField.getColumnCount() - 1; ++j) {
                Button field = new Button();
                field.setMinSize(10, 10);
                field.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                field.setPrefSize(100, 100);
                field.setOnAction(new ClickOnGameFieldHandler());
                opponentGameField.add(field, j, i);
                opponentButtons[i][j] = field;
                Button myField = new Button();
                myField.setMinSize(10, 10);
                myField.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                myField.setPrefSize(100, 100);
                myField.setFocusTraversable(false);
                myField.setMouseTransparent(true);
                if (myOcean.getShip(i, j).getShipType().length() == 0) {
                    myField.setStyle("-fx-base: aqua");
                } else {
                    myField.setStyle("-fx-base: red");
                }
                myGameField.add(myField, j, i);
                myButtons[i][j] = myField;
            }
        for (int i = 0; i < opponentGameField.getRowCount() - 1; ++i) {
            Label label = new Label(Integer.toString(i));
            label.setMinSize(10, 10);
            label.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            label.setPrefSize(100, 100);
            label.setAlignment(Pos.CENTER);
            opponentGameField.add(label, opponentGameField.getColumnCount() - 1, i);
            label = new Label(Integer.toString(i));
            label.setMinSize(10, 10);
            label.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            label.setPrefSize(100, 100);
            label.setAlignment(Pos.CENTER);
            myGameField.add(label, opponentGameField.getColumnCount() - 1, i);
        }
        for (int j = 0; j < opponentGameField.getColumnCount() - 1; ++j) {
            Label label = new Label(Integer.toString(j));
            label.setMinSize(10, 10);
            label.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            label.setPrefSize(100, 100);
            label.setAlignment(Pos.CENTER);
            opponentGameField.add(label, j, opponentGameField.getRowCount() - 1);
            label = new Label(Integer.toString(j));
            label.setMinSize(10, 10);
            label.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            label.setPrefSize(100, 100);
            label.setAlignment(Pos.CENTER);
            myGameField.add(label, j, opponentGameField.getRowCount() - 1);
        }
    }

    /**
     * Initializing the stage
     * (I don't think code-behind is something bad, so some elements were declared and/or styled in fxml,
     * for example mouseTransparency for log, its style and etc.,
     * but if code-behind is prohibited in this homework, please don't be strict to me)
     */
    @FXML
    void initialize() {
        log.setEditable(false);
        initGameFields();
        coordX = -1;
        coordY = -1;
        updateLog("Welcome to BattleShip game");
        x.setOnAction(new CoordinatesChangedHandler());
        y.setOnAction(new CoordinatesChangedHandler());
        buttonCancel.setOnAction(new CancelHandler());
        buttonInfo.setOnAction(new ClickOnInfoHandler());
    }
}
