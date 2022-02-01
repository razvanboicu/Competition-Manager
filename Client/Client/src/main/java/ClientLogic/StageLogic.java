package ClientLogic;

import Logic.User;

import java.util.ArrayList;
import java.util.List;

public class StageLogic {
    public List<String> HandleStageList() {
        ClientSingleton.getInstance().getClient().SendMessage("STAGELIST");
        String[] tokens= ClientSingleton.getInstance().getClient().ReceiveMessage().split(";");
        List<String> response = new ArrayList<>();
        for(int i = 0; i < tokens.length; i++)
            if(!tokens[i].equals("NONE"))
            response.add(tokens[i]);
        return response;
    }

    public void SendNewStage(String text) {
        ClientSingleton.getInstance().getClient().SendMessage("NEWSTAGE;"+text);
        String receive=ClientSingleton.getInstance().getClient().ReceiveMessage();
    }

    public void SendDeleteStage(String stageName)
    {
        ClientSingleton.getInstance().getClient().SendMessage("DELETESTAGE;"+stageName);
        String receive=ClientSingleton.getInstance().getClient().ReceiveMessage();
    }

    public void SendEditStage(String initialstageName,String stageName)
    {
        ClientSingleton.getInstance().getClient().SendMessage("UPDATESTAGE;"+initialstageName+';'+stageName);
        String receive=ClientSingleton.getInstance().getClient().ReceiveMessage();
    }

    public int SendCurrentStage()
    {
        ClientSingleton.getInstance().getClient().SendMessage("CURRENTSTAGE");
        return Integer.parseInt(ClientSingleton.getInstance().getClient().ReceiveMessage());
    }

    public String GetCurrentStageInfo(int currentStage,String username)
    {
        ClientSingleton.getInstance().getClient().SendMessage("STAGEINFO;"+currentStage+';'+username);
        return ClientSingleton.getInstance().getClient().ReceiveMessage();
    }

    public int GetNextStage(int Stage) {
      ClientSingleton.getInstance().getClient().SendMessage("NEXTSTAGE;"+String.valueOf(Stage));
        String response=ClientSingleton.getInstance().getClient().ReceiveMessage();
        if(response.equals("LAST STAGE"))
            return -1;
        else return Integer.valueOf(response);
    }

    public int GetPreviousStage(int Stage) {
        ClientSingleton.getInstance().getClient().SendMessage("PREVIOUSSTAGE;"+String.valueOf(Stage));
        String response=ClientSingleton.getInstance().getClient().ReceiveMessage();
        if(response.equals("FIRST STAGE"))
            return -1;
        else return Integer.valueOf(response);
    }
}
