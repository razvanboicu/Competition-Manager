package Context;

public class TeamEditContext {
    private static final TeamEditContext instance=new TeamEditContext();

    public static TeamEditContext getInstance()
    {
        return instance;
    }

    private String teamName;
    private volatile boolean isEdited;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }
}
