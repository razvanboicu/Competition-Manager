package Context;

import ClientLogic.Client;
import ClientLogic.ClientSingleton;

import java.io.IOException;
import java.net.Socket;

public class UserEditContext {
    private final static UserEditContext instance = new UserEditContext();

    public static UserEditContext getInstance()
    {
        return instance;
    }

    private String username;
    private String team;
    private volatile boolean isEdited = false;


    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
