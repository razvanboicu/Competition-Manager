package Controllers;

import Context.UserEditContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class UserEditController implements Initializable {

    @FXML
    private TextField tf_editUsername;
    @FXML
    private TextField tf_editTeam;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tf_editUsername.setText(UserEditContext.getInstance().getUsername());
        tf_editTeam.setText(UserEditContext.getInstance().getTeam());
    }

    @FXML
    void onEditClicked(MouseEvent event) {
      if(!tf_editTeam.getText().isEmpty() && !tf_editUsername.getText().isEmpty()) {
          UserEditContext.getInstance().setUsername(tf_editUsername.getText());
          UserEditContext.getInstance().setTeam(tf_editTeam.getText());
          UserEditContext.getInstance().setEdited(true);
          ((Node)(event.getSource())).getScene().getWindow().hide();
      }
    }

}