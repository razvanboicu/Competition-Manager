package ClientLogic;

import Logic.TeamClasament;
import Logic.User;
import Logic.UserClasament;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameLogic {
    public String HandleGameStart()
    {
        ClientSingleton.getInstance().getClient().SendMessage("GAMECHECK;");
        return ClientSingleton.getInstance().getClient().ReceiveMessage();
    }

    public List<UserClasament> HandleUserClasament(int stage)
    {
        ClientSingleton.getInstance().getClient().SendMessage("USERCLASAMENT"+';'+String.valueOf(stage));
        String[] tokens= ClientSingleton.getInstance().getClient().ReceiveMessage().split(";");
        List<UserClasament> response = new ArrayList<>();
        for(int i = 0; i<tokens.length; i=i+2)
            response.add(new UserClasament(tokens[i],Integer.parseInt(tokens[i+1])));
        response.sort(Comparator.comparing(UserClasament::getScore).reversed());
        return response;
    }

    public List<TeamClasament> HandleTeamClasament(int stage)
    {
        ClientSingleton.getInstance().getClient().SendMessage("TEAMCLASAMENT"+';'+String.valueOf(stage));
        String[] tokens= ClientSingleton.getInstance().getClient().ReceiveMessage().split(";");
        List<TeamClasament> response = new ArrayList<>();
        for(int i = 0; i<tokens.length; i=i+2)
            response.add(new TeamClasament(tokens[i],Integer.parseInt(tokens[i+1])));
        response.sort(Comparator.comparing(TeamClasament::getScore).reversed());
        return response;
    }

    public String SendNewEntry(String username,String score)
    {
        ClientSingleton.getInstance().getClient().SendMessage("NEWENTRY;"+username+';'+score);
        return ClientSingleton.getInstance().getClient().ReceiveMessage();
    }

    public boolean GetEntryStatusForUser(String username, int StageID)
    {
        ClientSingleton.getInstance().getClient().SendMessage("ENTRYSTATUS;"+username+';'+String.valueOf(StageID));
        if(ClientSingleton.getInstance().getClient().ReceiveMessage().equals("TRUE"))
            return true;
        return false;
    }

    public boolean CheckGameStatus(int idStage)
    {
        ClientSingleton.getInstance().getClient().SendMessage("GAMESTATUS;"+idStage);
        if(ClientSingleton.getInstance().getClient().ReceiveMessage().equals("COMPLETED"))
            return true;
        return false;
    }

    public List<UserClasament> GetFinalUserClasament()
    {
        ClientSingleton.getInstance().getClient().SendMessage("FINALUSERCLASAMENT");
        String[] tokens= ClientSingleton.getInstance().getClient().ReceiveMessage().split(";");
        List<UserClasament> response = new ArrayList<>();
        for(int i = 0; i<tokens.length; i=i+2)
            response.add(new UserClasament(tokens[i],Integer.parseInt(tokens[i+1])));
        response.sort(Comparator.comparing(UserClasament::getScore).reversed());
        return response;
    }

    public List<TeamClasament> GetFinalTeamClasament()
    {
        ClientSingleton.getInstance().getClient().SendMessage("FINALTEAMCLASAMENT");
        String[] tokens= ClientSingleton.getInstance().getClient().ReceiveMessage().split(";");
        List<TeamClasament> response = new ArrayList<>();
        for(int i = 0; i<tokens.length; i=i+2)
            response.add(new TeamClasament(tokens[i],Integer.parseInt(tokens[i+1])));
        response.sort(Comparator.comparing(TeamClasament::getScore).reversed());
        return response;
    }

    public boolean GetGameCompletionStatus()
    {
        ClientSingleton.getInstance().getClient().SendMessage("GAMECOMPLETE");
        if(ClientSingleton.getInstance().getClient().ReceiveMessage().equals("TRUE"))
            return true;
        return false;
    }
}
