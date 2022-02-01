package Scenes;

import Controllers.ParticipantMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Scenes {

    static public void LoginWindow()
    {
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(Scenes.class.getResource("../login.fxml"));
        Parent root = null;
        root = (Parent) fxmlLoader.load();
        Scene scene = new Scene((Parent)root);
        Stage stage=new Stage();
        stage.setTitle("The competition project - Boicu Razvan");
        stage.setScene(scene);
        stage.show();}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void ParticipantWindow(String username)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Scenes.class.getResource("../participantmenu.fxml"));
            Parent root = null;
            root = (Parent) fxmlLoader.load();
            Scene scene = new Scene((Parent)root);
            Stage stage=new Stage();
            stage.setTitle("The competition project - Boicu Razvan");
            stage.setScene(scene);
            ParticipantMenuController controller=fxmlLoader.getController();
            controller.setUsername(username);
            stage.show();}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void AdminWindow()
    {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Scenes.class.getResource("../adminmenu.fxml"));
            Parent root = null;
            root = (Parent) fxmlLoader.load();
            Scene scene = new Scene((Parent)root);
            Stage stage=new Stage();
            stage.setTitle("The competition project - Boicu Razvan");
            stage.setScene(scene);
            stage.show();}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void UserEditWindow()
    {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Scenes.class.getResource("../useredit.fxml"));
            Parent root = null;
            root = (Parent) fxmlLoader.load();
            Scene scene = new Scene((Parent)root);
            Stage stage=new Stage();
            stage.setTitle("The competition project - Boicu Razvan");
            stage.setScene(scene);
            stage.show();}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void StageEditWindow()
    {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Scenes.class.getResource("../stageedit.fxml"));
            Parent root = null;
            root = (Parent) fxmlLoader.load();
            Scene scene = new Scene((Parent)root);
            Stage stage=new Stage();
            stage.setTitle("The competition project - Boicu Razvan");
            stage.setScene(scene);
            stage.show();}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void EditTeamWindow()
    {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Scenes.class.getResource("../teamedit.fxml"));
            Parent root = null;
            root = (Parent) fxmlLoader.load();
            Scene scene = new Scene((Parent)root);
            Stage stage=new Stage();
            stage.setTitle("The competition project - Boicu Razvan");
            stage.setScene(scene);
            stage.show();}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void AdminGameMenuWindow()
    {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Scenes.class.getResource("../admingamemenu.fxml"));
            Parent root = null;
            root = (Parent) fxmlLoader.load();
            Scene scene = new Scene((Parent)root);
            Stage stage=new Stage();
            stage.setTitle("The competition project - Boicu Razvan");
            stage.setScene(scene);
            stage.show();}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void FinalClasamentWindow()
    {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Scenes.class.getResource("../finalClasament.fxml"));
            Parent root = null;
            root = (Parent) fxmlLoader.load();
            Scene scene = new Scene((Parent)root);
            Stage stage=new Stage();
            stage.setTitle("The competition project - Boicu Razvan");
            stage.setScene(scene);
            stage.show();}
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
