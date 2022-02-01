package ServerLogic;

import database.dao.TeamDao;
import database.model.StageEntity;
import database.model.TeamEntity;

import java.util.List;

public class TeamLogic {
    private static TeamDao teamDao = new TeamDao();

    static public String GetListOfTeams(){
        List<TeamEntity> listOfTeams = teamDao.getAll();
        String response = new String();

        for(TeamEntity team : listOfTeams ){
            if(!team.getNameTeam().equals("STAFF"))
            response += team.getNameTeam()+";";
        }
        if(response.isEmpty())
            response = "NONE";
        return response;
    }
    static public String UpdateNewTeam(String teamName)
    {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setNameTeam(teamName);
        teamDao.create(teamEntity);
        return "ADDED";
    }

    static public String DeleteTeam(String teamName){
        teamDao.delete(teamName);
        return "DELETED";
    }

    static public String UpdateTeam(String initialTeamName,String teamName)
    {
        teamDao.update(initialTeamName,teamName);
        return "UPDATED";
    }
}
