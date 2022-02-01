package Controllers;

import Context.StageEditContext;
import Context.TeamEditContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class TeamEditController implements Initializable {

    @FXML
    private TextField tf_new_team_name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tf_new_team_name.setText(TeamEditContext.getInstance().getTeamName());
    }

    @FXML
    void onEditClicked(MouseEvent event) {
        if(!tf_new_team_name.getText().isEmpty()){
            TeamEditContext.getInstance().setTeamName(tf_new_team_name.getText());
            TeamEditContext.getInstance().setEdited(true);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
}
