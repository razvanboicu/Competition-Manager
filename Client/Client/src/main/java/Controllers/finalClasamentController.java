package Controllers;

import ClientLogic.GameLogic;
import Logic.TeamClasament;
import Logic.UserClasament;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class finalClasamentController implements Initializable {

    @FXML
    private TableView<TeamClasament> list_team;

    @FXML
    private TableView<UserClasament> list_users;

    @FXML
    private TableColumn<TeamClasament, String> team;

    @FXML
    private TableColumn<TeamClasament, Integer> teamscore;

    @FXML
    private TableColumn<UserClasament, String> username;

    @FXML
    private TableColumn<UserClasament, Integer> userscore;

    private GameLogic gameLogic=new GameLogic();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teamscore.setCellValueFactory(new PropertyValueFactory<TeamClasament,Integer>("score"));
        team.setCellValueFactory(new PropertyValueFactory<TeamClasament,String>("teamName"));
        userscore.setCellValueFactory(new PropertyValueFactory<UserClasament,Integer>("score"));
        username.setCellValueFactory(new PropertyValueFactory<UserClasament,String>("username"));
        list_users.setItems(FXCollections.observableList(gameLogic.GetFinalUserClasament()));
        list_team.setItems(FXCollections.observableList(gameLogic.GetFinalTeamClasament()));
    }
}
