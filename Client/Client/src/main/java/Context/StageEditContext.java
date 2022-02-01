package Context;

import javafx.stage.Stage;

public class StageEditContext {
    private final static StageEditContext instance=new StageEditContext();

    static public StageEditContext getInstance()
    {
        return instance;
    }

    private String Stage;
    private volatile boolean isEdited=false;

     public String getStage() {
        return Stage;
    }

   public void setStage(String initialStage) {
        this.Stage = initialStage;
    }


    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }
}
