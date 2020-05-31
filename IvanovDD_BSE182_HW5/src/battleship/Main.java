package battleship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static boolean isServer;

    /**
     * Gets primaryStage from the given fxml file
     * Launches primaryStage
     *
     * @param primaryStage stage to launch
     */
    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            loader.setControllerFactory(c -> new MenuController(isServer));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Battleship Game");
        try {
            primaryStage.setScene(new Scene(root));
            primaryStage.getIcons().add(new Image("file:../../resources/icon.png"));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            isServer = args[0].equals("Server");
            launch(args);
        } catch (Exception e) {
            System.out.println("Enter role as Server / Client as commandline argument");
        }
    }
}
