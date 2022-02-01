package Controllers;

import ClientLogic.Client;
import ClientLogic.ClientSingleton;
import ClientLogic.GameLogic;
import ClientLogic.StageLogic;
import Logic.TeamClasament;
import Logic.User;
import Logic.UserClasament;
import Scenes.Scenes;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ParticipantMenuController implements Initializable {

    @FXML
    private ProgressIndicator progress;

    @FXML
    private RadioButton rb_teamclasament;

    @FXML
    private RadioButton rb_userclasament;

    @FXML
    private Text tf_currentStageText;

    @FXML
    private Button send_score_button;

    @FXML
    private Button finalClasament;

    @FXML
    private Text stage_name;

    @FXML
    private Text text_username;

    @FXML
    private TextField tf_score;

    @FXML
    private TableView<UserClasament> tv_UserClasament;

    @FXML
    private TableView<TeamClasament> tv_TeamClasament;

    @FXML
    private TableColumn<UserClasament, Integer> user_score;

    @FXML
    private TableColumn<UserClasament, String> user_entry;

    @FXML
    private TableColumn<TeamClasament, Integer> team_score;

    @FXML
    private TableColumn<TeamClasament, String> team_entry;

    private GameLogic gameLogic=new GameLogic();
    private StageLogic stageLogic=new StageLogic();

    private int CurrentStage=stageLogic.SendCurrentStage();
    private int FocusedStage=CurrentStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInfoForCurrentStage();
       Thread thread= new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   Client threadSocket = new Client(new Socket("localhost", 27017));
                   threadSocket.SendMessage("NOTIFICATIONTHREAD");
                   threadSocket.ReceiveMessage();
                   while (true) {
                       String user = threadSocket.ReceiveMessage(); //blocking function
                       if (user.equals(text_username.getText())&& tf_score.getText().equals("0")) {
                           threadSocket.SendMessage("FOUND");
                           Platform.runLater(new Runnable() {
                               @Override
                               public void run() {
                                   Alert notif = new Alert(Alert.AlertType.INFORMATION, "NOTIFCATION", ButtonType.CLOSE);
                                   notif.setContentText("ADMIN wants you to type in your score.");
                                   notif.show();
                               }
                           });
                       }
                       else threadSocket.SendMessage("NOT FOUND");
                   }

               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       });
       thread.start();
    }


    @FXML
    void onSendScoreClicked(MouseEvent event) {
        if(tf_score.isEditable()) {
            gameLogic.SendNewEntry(text_username.getText(), tf_score.getText());
            tf_score.setEditable(false);
            progress.setProgress(100);
        }
    }

    public void setUsername(String username)
    {
        text_username.setText(username);
        if(gameLogic.GetEntryStatusForUser(text_username.getText(),CurrentStage))
            tf_score.setEditable(false);
        else tf_score.setEditable(true);
        String[] info=stageLogic.GetCurrentStageInfo(CurrentStage,text_username.getText()).split(";");
        tf_score.setText(info[1]);
        stage_name.setText(info[0]);
        if(!tf_score.getText().equals("0"))
            progress.setProgress(100);
        else progress.setProgress(0);
    }

    public void setInfoForCurrentStage()
    {
        team_score.setCellValueFactory(new PropertyValueFactory<TeamClasament,Integer>("score"));
        team_entry.setCellValueFactory(new PropertyValueFactory<TeamClasament,String>("teamName"));
        user_score.setCellValueFactory(new PropertyValueFactory<UserClasament,Integer>("score"));
        user_entry.setCellValueFactory(new PropertyValueFactory<UserClasament,String>("username"));
        tv_UserClasament.setItems(FXCollections.observableList(gameLogic.HandleUserClasament(CurrentStage)));
        tv_TeamClasament.setItems((FXCollections.observableList(gameLogic.HandleTeamClasament(CurrentStage))));
        if(!gameLogic.CheckGameStatus(CurrentStage)){
            tv_TeamClasament.setVisible(false);
            tv_UserClasament.setVisible(false);
        }
        else
        {
            tv_TeamClasament.setVisible(true);
            tv_UserClasament.setVisible(true);
        }
        if(CurrentStage==FocusedStage)
            tf_currentStageText.setVisible(true);
        else tf_currentStageText.setVisible(false);

        if(gameLogic.GetGameCompletionStatus())
            finalClasament.setVisible(true);
    }

    public void setInfoForStage(int Stage)
    {
        team_score.setCellValueFactory(new PropertyValueFactory<TeamClasament,Integer>("score"));
        team_entry.setCellValueFactory(new PropertyValueFactory<TeamClasament,String>("teamName"));
        user_score.setCellValueFactory(new PropertyValueFactory<UserClasament,Integer>("score"));
        user_entry.setCellValueFactory(new PropertyValueFactory<UserClasament,String>("username"));
        tv_UserClasament.setItems(FXCollections.observableList(gameLogic.HandleUserClasament(Stage)));
        tv_TeamClasament.setItems((FXCollections.observableList(gameLogic.HandleTeamClasament(Stage))));
        if(gameLogic.GetEntryStatusForUser(text_username.getText(),FocusedStage))
            tf_score.setEditable(false);
        else tf_score.setEditable(true);
        String[] info=stageLogic.GetCurrentStageInfo(Stage,text_username.getText()).split(";");
        tf_score.setText(info[1]);
        stage_name.setText(info[0]);
        if(!tf_score.getText().equals("0"))
            progress.setProgress(100);
        else progress.setProgress(0);
        if(!gameLogic.CheckGameStatus(Stage)){
            tv_TeamClasament.setVisible(false);
            tv_UserClasament.setVisible(false);
        }
        else
        {
            tv_TeamClasament.setVisible(true);
            tv_UserClasament.setVisible(true);
        }
        if(CurrentStage==FocusedStage)
            tf_currentStageText.setVisible(true);
        else tf_currentStageText.setVisible(false);

        if(gameLogic.GetGameCompletionStatus())
            finalClasament.setVisible(true);
    }

    @FXML
    void onNextStageClicked(MouseEvent event) {
        if(stageLogic.GetNextStage(FocusedStage)==-1)
        {
            Alert error=new Alert(Alert.AlertType.ERROR,"NO NEXT STAGE FOUND",ButtonType.CLOSE);
            error.show();
        }
        else
        {
            FocusedStage=stageLogic.GetNextStage(FocusedStage);
            setInfoForStage(FocusedStage);
        }
    }

    @FXML
    void onPreviousStageClicked(MouseEvent event) {
        if(stageLogic.GetPreviousStage(FocusedStage)==-1)
        {
            Alert error=new Alert(Alert.AlertType.ERROR,"NO PREVIOUS STAGE FOUND",ButtonType.CLOSE);
            error.show();
        }
        else
        {
            FocusedStage=stageLogic.GetPreviousStage(FocusedStage);
            setInfoForStage(FocusedStage);
        }
    }

    @FXML
    void onRefreshClicked(MouseEvent event) {
        CurrentStage=stageLogic.SendCurrentStage();
        setInfoForStage(CurrentStage);
    }
    @FXML
    void onFinalClasamentClicked(MouseEvent event) {
        Scenes.FinalClasamentWindow();
    }
}
