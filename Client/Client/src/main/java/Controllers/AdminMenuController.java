package Controllers;

import ClientLogic.GameLogic;
import ClientLogic.StageLogic;
import ClientLogic.TeamLogic;
import ClientLogic.UserLogic;
import Context.StageEditContext;
import Context.TeamEditContext;
import Context.UserEditContext;
import Logic.User;
import Scenes.Scenes;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
   @FXML
   private TextField tf_username;
   @FXML
   private TextField tf_password;
   @FXML
   private TextField tf_first_name;
   @FXML
   private TextField tf_second_name;
   @FXML
   private TextField tf_role;
   @FXML
   private TextField tf_team;
   @FXML
   private TableView<User> list_users;
   @FXML
   private TableColumn<User,String> username;
   @FXML
   private TableColumn<User,String> team;
   @FXML
   private ListView<String> stages_list;
   @FXML
   private ListView<String> teams_list;
   @FXML
   private TextField tf_stage_name;
   @FXML
   private TextField tf_team_name;

   private TeamLogic teamLogic = new TeamLogic();
   private UserLogic userLogic = new UserLogic();
   private StageLogic stageLogic = new StageLogic();
   private GameLogic gameLogic = new GameLogic();

   private ObservableList<User> ListOfUsernames = FXCollections.observableArrayList(userLogic.HandleUserList());
   private  ObservableList<String> listOfStages = FXCollections.observableArrayList(stageLogic.HandleStageList());
   private ObservableList<String> listOfTeams = FXCollections.observableArrayList(teamLogic.HandleTeamList());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list_users.setItems(ListOfUsernames);
        username.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        team.setCellValueFactory(new PropertyValueFactory<User,String>("team"));
        stages_list.setItems(listOfStages);
        teams_list.setItems(listOfTeams);
    }

    public void onCreateUserClicked(MouseEvent mouseEvent) {
        if(CheckUserFields())
        {
            userLogic.SendNewUser(tf_username.getText(),tf_password.getText(),tf_first_name.getText(),tf_second_name.getText(),tf_role.getText(),tf_team.getText());
            ListOfUsernames.add(new User(tf_username.getText(),tf_team.getText()));
            list_users.setItems(ListOfUsernames);
        }
    }
    public boolean CheckUserFields()
    {
        if(tf_username.getText().isEmpty())
            return false;
        if(tf_password.getText().isEmpty())
            return false;
        if(tf_first_name.getText().isEmpty())
            return false;
        if(tf_second_name.getText().isEmpty())
            return false;
        if(tf_role.getText().isEmpty())
            return false;
        if(tf_team.getText().isEmpty())
            return false;
        return true;
    }

    public void onCreateStageClicked(MouseEvent mouseEvent) {
        if(!tf_stage_name.getText().isEmpty()) {
            stageLogic.SendNewStage(tf_stage_name.getText());
            listOfStages.add(tf_stage_name.getText());
            stages_list.setItems(listOfStages);
        }
    }


    public void onCreateTeamClicked(MouseEvent mouseEvent) {
        if (!tf_team_name.getText().isEmpty()) {
            teamLogic.SendNewTeam(tf_team_name.getText());
            listOfTeams.add(tf_team_name.getText());
            teams_list.setItems(listOfTeams);
        }
    }

    public void onCreateGameClicked(MouseEvent mouseEvent) {

        if(gameLogic.HandleGameStart().equals("POSSIBLE")) {
            Scenes.AdminGameMenuWindow();
            gameLogic.SendNewEntry("admin", "0");
            ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
        }
        else
        {
            Alert alert=new Alert(Alert.AlertType.ERROR,"Game can't start\nRules:Every team must have between 2 or 5 members");
            alert.show();
        }
    }

    public void onDeleteUserClicked(MouseEvent mouseEvent) {
        userLogic.SendDeleteUser(list_users.getSelectionModel().getSelectedItem().getUsername());
        list_users.getItems().removeAll(list_users.getSelectionModel().getSelectedItem());
    }

    public void onEditUserClicked(MouseEvent mouseEvent) {
        UserEditContext.getInstance().setUsername(list_users.getSelectionModel().getSelectedItem().getUsername());
        UserEditContext.getInstance().setTeam(list_users.getSelectionModel().getSelectedItem().getTeam());
        Scenes.UserEditWindow();
        String initialUsername = list_users.getSelectionModel().getSelectedItem().getUsername();
        Thread thread = new Thread(new Runnable() {//SPAGHETTI THREAD
            @Override
            public void run() {
               do {
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               } while (!UserEditContext.getInstance().isEdited());
                for(int index=0; index<list_users.getItems().size(); index++) {
                    if (list_users.getItems().get(index).getUsername() != UserEditContext.getInstance().getUsername() && list_users.getItems().get(index).getUsername() == initialUsername) {
                        list_users.getItems().set(index, new User(UserEditContext.getInstance().getUsername(), UserEditContext.getInstance().getTeam()));
                        userLogic.SendEditUser(initialUsername, list_users.getItems().get(index).getUsername(), list_users.getItems().get(index).getTeam());
                        UserEditContext.getInstance().setEdited(false);
                    }
                }
            }
        });
        thread.start();
    }

    public void onDeleteStageClicked(MouseEvent mouseEvent) {
        stageLogic.SendDeleteStage(stages_list.getSelectionModel().getSelectedItem());
        stages_list.getItems().removeAll(stages_list.getSelectionModel().getSelectedItem());
    }

    public void onEditStageClicked(MouseEvent mouseEvent) {
        StageEditContext.getInstance().setStage(stages_list.getSelectionModel().getSelectedItem());
        Scenes.StageEditWindow();
        String initialStage=stages_list.getSelectionModel().getSelectedItem();
        Thread thread=new Thread(new Runnable() {//SPAGHETTI THREAD 2
            @Override
            public void run() {
                while (!StageEditContext.getInstance().isEdited()) {
                    try {
                        Thread.sleep(1000);//somn usor
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int index = 0; index < stages_list.getItems().size(); index++)
                    if (stages_list.getItems().get(index) == initialStage && initialStage != StageEditContext.getInstance().getStage()) {
                        stageLogic.SendEditStage(initialStage,StageEditContext.getInstance().getStage());
                        int finalIndex = index;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                stages_list.getItems().set(finalIndex, StageEditContext.getInstance().getStage());
                            }
                        });
                        StageEditContext.getInstance().setEdited(false);
                    }
            } });
        thread.start();
    }

    public void onDeleteTeamClicked(MouseEvent mouseEvent) {
        String toDelete = teams_list.getSelectionModel().getSelectedItem();
        teamLogic.SendDeleteTeam(teams_list.getSelectionModel().getSelectedItem());
        String intialTeam=teams_list.getSelectionModel().getSelectedItem();
        teams_list.getItems().removeAll(teams_list.getSelectionModel().getSelectedItem());
        for (int index = 0; index < list_users.getItems().size(); index++)
        {
            System.out.println(list_users.getItems().get(index).getTeam());
            if(list_users.getItems().get(index).getTeam().equals(intialTeam))
            {
                list_users.getItems().remove(index);
            }
        }
    }

    public void onEditTeamClicked(MouseEvent mouseEvent) {
        TeamEditContext.getInstance().setTeamName(teams_list.getSelectionModel().getSelectedItem());
        Scenes.EditTeamWindow();
        String initialTeam=teams_list.getSelectionModel().getSelectedItem();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(!TeamEditContext.getInstance().isEdited()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for(int index=0;index<teams_list.getItems().size();index++)
                {
                    if(teams_list.getItems().get(index)==initialTeam && initialTeam!=TeamEditContext.getInstance().getTeamName())
                    {
                        teamLogic.SendEditTeam(initialTeam,TeamEditContext.getInstance().getTeamName());
                        int finalIndex = index;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("got here");
                                teams_list.getItems().set(finalIndex,TeamEditContext.getInstance().getTeamName());
                                SetUsers(initialTeam,TeamEditContext.getInstance().getTeamName());
                            }
                        });
                        TeamEditContext.getInstance().setEdited(false);
                    }
                }
            }
        });
        thread.start();
    }

    private void SetUsers(String initialTeam, String teamName) {
        for (int index=0;index<list_users.getItems().size();index++)
        {
            if(list_users.getItems().get(index).getTeam().equals(initialTeam))
                list_users.getItems().set(index,new User(list_users.getItems().get(index).getUsername(),teamName));
        }
    }
}
