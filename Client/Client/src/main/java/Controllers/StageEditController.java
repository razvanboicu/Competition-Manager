package Controllers;

import ClientLogic.StageLogic;
import Context.StageEditContext;
import Context.UserEditContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class StageEditController implements Initializable {
    @FXML
    private TextField tf_new_stage_name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tf_new_stage_name.setText(StageEditContext.getInstance().getStage());
    }

    public void onOKClicked(MouseEvent event) {
        if(!tf_new_stage_name.getText().isEmpty()) {
            StageEditContext.getInstance().setStage(tf_new_stage_name.getText());
            StageEditContext.getInstance().setEdited(true);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }
}
