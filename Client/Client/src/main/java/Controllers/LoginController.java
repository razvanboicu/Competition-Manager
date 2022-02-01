package Controllers;

import ClientLogic.Client;
import ClientLogic.GameLogic;
import ClientLogic.UserLogic;
import Scenes.Scenes;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button button_login;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;

    private UserLogic userLogic = new UserLogic();
    private GameLogic gameLogic= new GameLogic();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Empty
    }

    public void OnMouseClicked(MouseEvent mouseEvent) {
        if(tf_password.getText()!="" && tf_username.getText()!="") {
            String[] status=userLogic.HandleLogin(tf_username.getText(), tf_password.getText()).split(";");
            if(status[0].equals("PARTICIPANT") && status[1].equals("COMPLETE")) {
                ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
                Scenes.ParticipantWindow(tf_username.getText());
                return;
            }
            if(status[0].equals("ADMIN"))
            {
                if(status[1].equals("NOT COMPLETED")) {
                    ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
                    Scenes.AdminWindow();
                    return;
                }
                else
                {
                    ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
                    Scenes.AdminGameMenuWindow();
                    return;
                }
            }
            Alert alert=new Alert(Alert.AlertType.ERROR,"User does not exist or no game has started yet.");
            alert.show();
        }
    }
}

