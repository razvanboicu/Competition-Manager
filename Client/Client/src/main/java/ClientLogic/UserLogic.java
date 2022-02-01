package ClientLogic;

import Logic.User;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class UserLogic {

    public String HandleLogin(String username, String password)
    {
        ClientSingleton.getInstance().getClient().SendMessage("LOGIN;" + username + ';'+password);
        String response = ClientSingleton.getInstance().getClient().ReceiveMessage();
        return response;
    }

    public List<User> HandleUserList()
    {
        ClientSingleton.getInstance().getClient().SendMessage("USERLIST");
        String[] tokens= ClientSingleton.getInstance().getClient().ReceiveMessage().split(";");
        List<User> response = new ArrayList<>();
        for(int i = 0; i<tokens.length; i=i+2)
            response.add(new User(tokens[i],tokens[i+1]));
        return response;
    }

    public void SendNewUser(String username,String password, String firstname,String lastname,String role,String team)
    {
        String toSend="NEWUSER;"+username+';'+password+';'+firstname+';'+lastname+';'+role+';'+team;
        ClientSingleton.getInstance().getClient().SendMessage(toSend);
        String receive=ClientSingleton.getInstance().getClient().ReceiveMessage();
    }

    public void SendDeleteUser(String username)
    {
        ClientSingleton.getInstance().getClient().SendMessage("DELETEUSER;"+username);
    }

    public void SendEditUser(String intialUsername,String username,String team)
    {
        ClientSingleton.getInstance().getClient().SendMessage("UPDATEUSER"+';'+intialUsername+';'+username+';'+team);
    }

    public String SendNotification(String username)
    {
        ClientSingleton.getInstance().getClient().SendMessage("NOTIFICATION;"+username);
        return ClientSingleton.getInstance().getClient().ReceiveMessage();
    }
}
