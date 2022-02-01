import ClientLogic.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import Scenes.Scenes;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
       Scenes.LoginWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}