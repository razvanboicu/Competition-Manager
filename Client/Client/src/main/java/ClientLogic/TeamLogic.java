package ClientLogic;

import Logic.User;

import java.util.ArrayList;
import java.util.List;

public class TeamLogic {
     public List<String> HandleTeamList()
    {
        ClientSingleton.getInstance().getClient().SendMessage("TEAMLIST");
        String[] tokens= ClientSingleton.getInstance().getClient().ReceiveMessage().split(";");
        List<String> response = new ArrayList<>();
        for(int i = 0; i< tokens.length; i++)
            response.add(tokens[i]);
        return response;
    }

    public void SendNewTeam(String text) {
        ClientSingleton.getInstance().getClient().SendMessage("NEWTEAM;"+text);
        String receive = ClientSingleton.getInstance().getClient().ReceiveMessage();
    }

    public void SendDeleteTeam(String teamName){
         ClientSingleton.getInstance().getClient().SendMessage("DELETETEAM;"+teamName);
         ClientSingleton.getInstance().getClient().ReceiveMessage();
    }

    public void SendEditTeam(String initialTeamName, String teamName)
    {
        ClientSingleton.getInstance().getClient().SendMessage("UPDATETEAM;"+initialTeamName+';'+teamName);
        ClientSingleton.getInstance().getClient().ReceiveMessage();
    }
}
