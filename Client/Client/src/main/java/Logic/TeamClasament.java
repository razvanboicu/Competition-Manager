package Logic;

public class TeamClasament {
    private String teamName;
    private int score;

    public TeamClasament(String teamName,int score) {
        this.teamName = teamName;
        this.score=score;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
