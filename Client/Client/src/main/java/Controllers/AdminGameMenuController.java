package Controllers;

import ClientLogic.GameLogic;
import ClientLogic.StageLogic;
import ClientLogic.UserLogic;
import Logic.TeamClasament;
import Logic.User;
import Logic.UserClasament;
import Scenes.Scenes;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminGameMenuController implements Initializable {

    @FXML
    private ProgressIndicator progress;

    @FXML
    private Button finalClasament;

    @FXML
    private Text nameStage;

    @FXML
    private TableColumn<TeamClasament, String> team_column;

    @FXML
    private TableColumn<TeamClasament, Integer> teamscore_column;

    @FXML
    private TableView<TeamClasament> tv_teamClasament;

    @FXML
    private TableView<UserClasament> tv_userClasament;

    @FXML
    private TableView<User> list_users=new TableView<>();
    @FXML
    private TableColumn<User,String> username=new TableColumn<>();
    @FXML
    private TableColumn<User,String> team=new TableColumn<>();

    @FXML
    private TableColumn<UserClasament, String> username_column;

    @FXML
    private TableColumn<UserClasament, Integer> userscore_column;

    private UserLogic userLogic=new UserLogic();
    private GameLogic gameLogic= new GameLogic();
    private StageLogic stageLogic=new StageLogic();

    int CurrentStage=stageLogic.SendCurrentStage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list_users.setItems(FXCollections.observableArrayList(userLogic.HandleUserList()));
        username.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        team.setCellValueFactory(new PropertyValueFactory<User,String>("team"));
        SetInfoForStage();
    }

    @FXML
    void onNextStageClicked(MouseEvent event) {
        if(stageLogic.GetNextStage(CurrentStage)==-1)
        {
            Alert error=new Alert(Alert.AlertType.ERROR,"NO NEXT STAGE FOUND",ButtonType.CLOSE);
            error.show();
        }
        else
        {
            if(stageLogic.GetNextStage(CurrentStage)!=-1)
                CurrentStage=stageLogic.GetNextStage(CurrentStage);
            SetInfoForStage();
        }
    }

    @FXML
    void onNotifyClicked(MouseEvent event) {
        String response = userLogic.SendNotification(list_users.getSelectionModel().getSelectedItem().getUsername());
    }

    @FXML
    void onPreviousStageClicked(MouseEvent event) {
        if(stageLogic.GetPreviousStage(CurrentStage)==-1)
        {
            Alert error=new Alert(Alert.AlertType.ERROR,"NO NEXT STAGE FOUND",ButtonType.CLOSE);
            error.show();
        }
        else
        {
            if(stageLogic.GetPreviousStage(CurrentStage)!=-1)
            CurrentStage=stageLogic.GetPreviousStage(CurrentStage);
            SetInfoForStage();
        }
    }

    @FXML
    void onRefreshClicked(MouseEvent event) {
       SetInfoForStage();
        if(gameLogic.GetGameCompletionStatus())
            finalClasament.setVisible(true);
    }

    private void SetInfoForStage()
    {
        team_column.setCellValueFactory(new PropertyValueFactory<TeamClasament,String>("teamName"));
        teamscore_column.setCellValueFactory(new PropertyValueFactory<TeamClasament,Integer>("score"));
        username_column.setCellValueFactory(new PropertyValueFactory<UserClasament,String>("username"));
        userscore_column.setCellValueFactory(new PropertyValueFactory<UserClasament,Integer>("score"));
        tv_userClasament.setItems(FXCollections.observableList(gameLogic.HandleUserClasament(CurrentStage)));
        tv_teamClasament.setItems((FXCollections.observableList(gameLogic.HandleTeamClasament(CurrentStage))));
        String[] info=stageLogic.GetCurrentStageInfo(CurrentStage,"admin").split(";");
        nameStage.setText(info[0]);
        if(gameLogic.CheckGameStatus(CurrentStage))
            progress.setProgress(100);
        else progress.setProgress(0);
    }

    @FXML
    void onFinalClasamentClicked(MouseEvent event) {
        Scenes.FinalClasamentWindow();
    }
}