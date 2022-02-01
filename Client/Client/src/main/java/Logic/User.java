package Logic;

public class User {
    private String username;
    private String team;

    public User(String username,String team)
    {
        this.username = username;
        this.team = team;
    }

    public String getTeam() {
        return team;
    }

    public String getUsername() {
        return username;
    }

    public void setTeam(String team)
    {
        this.team=team;
    }

    public void setUsername(String username)
    {
        this.username=username;
    }
}
