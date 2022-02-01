package ServerLogic;

import com.sun.source.tree.Tree;
import database.dao.ParticipateDao;
import database.dao.StageDao;
import database.dao.TeamDao;
import database.dao.UserDao;
import database.model.ParticipateEntity;
import database.model.StageEntity;
import database.model.TeamEntity;
import database.model.UserEntity;

import java.util.*;

public class GameLogic {

    private static Integer idCurrentStage;
    private static SortedMap<Integer,TreeMap<String, Integer>> ListOfUserClasaments=new TreeMap<>(Collections.reverseOrder());
    private static SortedMap<Integer,TreeMap<String, Integer>> ListOfTeamClasaments=new TreeMap<>(Collections.reverseOrder());
    private static TeamDao teamDao=new TeamDao();
    private static UserDao userDao=new UserDao();
    private static StageDao stageDao=new StageDao();
    private static ParticipateDao participateDao=new ParticipateDao();

    public static String CheckPossibleGame()
    {
        List<TeamEntity> teams=teamDao.getAll();
        for(TeamEntity team:teams)
        {
            if(userDao.FindByTeam((int)team.getIdTeam()).size()!=0 && !team.getNameTeam().equals("STAFF")) {
                if (!(userDao.FindByTeam((int) team.getIdTeam()).size() >= 2 && userDao.FindByTeam((int) team.getIdTeam()).size() <= 5))
                {
                  return "NOT POSSIBLE";
                }
            }
        }
        return "POSSIBLE";
    }

    public static void GenerateClasament()
    {

        List<UserEntity> users=userDao.getAll();
        List<StageEntity> stages=stageDao.getAll();
        for(StageEntity stage:stages) {
            TreeMap<String, Integer> UserClasament=new TreeMap<>(Collections.reverseOrder());
            TreeMap<String, Integer> TeamClasament=new TreeMap<>(Collections.reverseOrder());
            for (UserEntity user : users) {
                if (!user.getRole().toString().equals("ADMIN"))
                    if (!participateDao.FindByUserIDAndStageID((int) user.getIdUser(), (int)stage.getIdStage()).isEmpty()) {
                        ParticipateEntity participateEntity = participateDao.FindByUserIDAndStageID((int) user.getIdUser(), (int)stage.getIdStage()).get(0);
                        if (participateEntity != null) {
                            UserClasament.put(user.getUsername(), participateEntity.getScore());
                        }
                    } else UserClasament.put(user.getUsername(), 0);
            }


            for (UserEntity user : users) {
                if (!teamDao.get(user.getIdTeam()).getNameTeam().equals("STAFF"))
                    TeamClasament.put(teamDao.get(user.getIdTeam()).getNameTeam(), 0);
            }
            for (UserEntity user : users) {
                if (!teamDao.get(user.getIdTeam()).getNameTeam().equals("STAFF")) {
                    if (!participateDao.FindByUserIDAndStageID((int) user.getIdUser(),  (int)stage.getIdStage()).isEmpty()) {
                        ParticipateEntity participateEntity = participateDao.FindByUserIDAndStageID((int) user.getIdUser(),  (int)stage.getIdStage()).get(0);
                        if (participateEntity != null) {
                            if (TeamClasament.get(teamDao.get(user.getIdTeam()).getNameTeam()) != null)
                                TeamClasament.put(teamDao.get(user.getIdTeam()).getNameTeam(), TeamClasament.get(teamDao.get(user.getIdTeam()).getNameTeam()) +
                                        participateEntity.getScore());
                        }
                    } else if (TeamClasament.get(teamDao.get(user.getIdTeam()).getNameTeam()) == null)
                        TeamClasament.put(teamDao.get(user.getIdTeam()).getNameTeam(), 0);
                }
            }
            System.out.println(UserClasament);
            System.out.println(TeamClasament);
            ListOfTeamClasaments.put( (int)stage.getIdStage(), TeamClasament);
            ListOfUserClasaments.put( (int)stage.getIdStage(), UserClasament);
            System.out.println(ListOfUserClasaments);
            System.out.println(ListOfTeamClasaments);
        }
    }

    private static void UpdateUserClasament(int StageID)
    {
        TreeMap<String, Integer> UserClasament=new TreeMap<>(Collections.reverseOrder());
        List<UserEntity> users=userDao.getAll();
        UserClasament.clear();
        for(UserEntity user:users)
        {
            if(!user.getRole().toString().equals("ADMIN"))
                if(!participateDao.FindByUserIDAndStageID((int) user.getIdUser(),StageID).isEmpty()) {
                    ParticipateEntity participateEntity =participateDao.FindByUserIDAndStageID((int) user.getIdUser(),StageID).get(0);
                    if (participateEntity != null) {
                        UserClasament.put(user.getUsername(), participateEntity.getScore());
                    }
                }else UserClasament.put(user.getUsername(), 0);
        }
        ListOfUserClasaments.put(StageID,UserClasament);
    }

    private static void UpdateTeamClasament(int StageID) {
        TreeMap<String, Integer> TeamClasament=new TreeMap<>(Collections.reverseOrder());
        List<UserEntity> users=userDao.getAll();
        TeamClasament.clear();
        for(UserEntity user:users)
        {
            if(!teamDao.get(user.getIdTeam()).getNameTeam().equals("STAFF"))
                TeamClasament.put(teamDao.get(user.getIdTeam()).getNameTeam(),0);
        }
        for(UserEntity user:users)
        {
            if(!teamDao.get(user.getIdTeam()).getNameTeam().equals("STAFF")) {
                if(!participateDao.FindByUserIDAndStageID((int) user.getIdUser(),StageID).isEmpty()) {
                    ParticipateEntity participateEntity = participateDao.FindByUserIDAndStageID((int) user.getIdUser(),StageID).get(0);
                    if (participateEntity != null)
                        TeamClasament.put(teamDao.get(user.getIdTeam()).getNameTeam(), TeamClasament.get(teamDao.get(user.getIdTeam()).getNameTeam()) +
                                participateEntity.getScore());
                }
                else if(TeamClasament.get(teamDao.get(user.getIdTeam()).getNameTeam())==null)
                    TeamClasament.put(teamDao.get(user.getIdTeam()).getNameTeam(), 0);
            }
        }
        ListOfTeamClasaments.put(StageID,TeamClasament);
    }


    public static String SendUserClasament(int stage)
    {
        UpdateUserClasament(stage);
        String toSend=new String();
        for(SortedMap.Entry<String,Integer> element:ListOfUserClasaments.get(stage).entrySet())
        {
            toSend+=element.getKey()+';'+element.getValue().toString()+';';
        }
        return toSend;
    }

    public static String SendTeamClasament(int stage)
    {
        UpdateTeamClasament(stage);
        String toSend=new String();
        for(SortedMap.Entry<String,Integer> element:ListOfTeamClasaments.get(stage).entrySet())
        {
            System.out.println(ListOfTeamClasaments.get(stage).entrySet());
            toSend+=element.getKey()+';'+element.getValue().toString()+';';
        }
        return toSend;
    }

    public static String UpdateClasaments(String username, String score)
    {
        GenerateCurrentStage();
        ParticipateEntity newEntry=new ParticipateEntity();
        newEntry.setIdUser(userDao.FindByUser(username).get(0).getIdUser());
        newEntry.setIdStage(idCurrentStage);
        newEntry.setScore(Integer.parseInt(score));
        participateDao.create(newEntry);
        int size=UserLogic.GetNumberOfParticipants();
        if(participateDao.FindByStageID((int)newEntry.getIdStage()).size()>=UserLogic.GetNumberOfParticipants()) {
            for(ParticipateEntity entry:participateDao.FindByStageID((int)newEntry.getIdStage()))
                if(userDao.get(entry.getIdUser()).getRole().toString().equals("ADMIN"))
                    size=UserLogic.GetNumberOfParticipants()+1;
            if(participateDao.FindByStageID((int)newEntry.getIdStage()).size()==size) {
                stageDao.updateStatus(true, newEntry.getIdStage());
                GenerateCurrentStage();
                return "COMPLETE";
            }
        }
        return "NOT COMPLETE";
    }

    public static boolean CheckIfStageFinished()
    {
        if(participateDao.FindByStageID(idCurrentStage).size()==userDao.getAll().size()-1)
            return true;
        return false;
    }

    public static String GetEntryStatusForUser(String username,int StageID)
    {
        if(ListOfUserClasaments.get(StageID).get(username)!=0)
            return "TRUE";
        return "FALSE";
    }

    public static void GenerateCurrentStage()
    {
        StageDao update=new StageDao();
        List<StageEntity> stages=update.getAll();
        for(StageEntity stage:stages)
        {
            if(participateDao.FindByStageID((int)stage.getIdStage()).size()>=userDao.getAll().size()-1)
            {
                stageDao.updateStatus(true,stage.getIdStage());
            }
        }

        for(StageEntity stage:stages)
        {
            System.out.println(stage.getIdStage());
            if(!stage.isStatus()) {
                idCurrentStage = (int) stage.getIdStage();
                return;
            }
        }
        idCurrentStage=(int)stages.get(0).getIdStage();
    }

    public static String GetGameStatus(int stageID)
    {
        StageDao updatedDAO=new StageDao();
        if(updatedDAO.get(stageID).isStatus())
            return "COMPLETED";
        else return "NOT COMPLETED";
    }

    public static String GetGameCompletionStatus()
    {
        StageDao DAO=new StageDao();
        List<StageEntity> stages=DAO.getAll();
        for(StageEntity stage:stages)
        {
            if(stage.isStatus()==false)
                return "FALSE";
        }
        return "TRUE";
    }

    public static String GetFinalUserClasament()
    {
        TreeMap<String,Integer> FinalUserClasament=new TreeMap<>();
        for(SortedMap.Entry<Integer,TreeMap<String,Integer>> element:ListOfUserClasaments.entrySet())
        {
            System.out.println(element);
            for(SortedMap.Entry<String,Integer> clasament:element.getValue().entrySet())
            {
                if(FinalUserClasament.get(clasament.getKey())!=null)
                FinalUserClasament.put(clasament.getKey(),clasament.getValue()+FinalUserClasament.get(clasament.getKey()));
                else
                    FinalUserClasament.put(clasament.getKey(),clasament.getValue());
            }
        }
        String toSend=new String();
        for(SortedMap.Entry<String,Integer> element:FinalUserClasament.entrySet())
        {
            toSend+=element.getKey()+';'+element.getValue().toString()+';';
        }
        return toSend;
    }

    public static String GetFinalTeamClasament()
    {
        TreeMap<String,Integer> FinalTeamClasament=new TreeMap<>();
        for(SortedMap.Entry<Integer,TreeMap<String,Integer>> element:ListOfTeamClasaments.entrySet())
        {
            for(SortedMap.Entry<String,Integer> clasament:element.getValue().entrySet())
            {
                if(FinalTeamClasament.get(clasament.getKey())!=null)
                    FinalTeamClasament.put(clasament.getKey(),clasament.getValue()+FinalTeamClasament.get(clasament.getKey()));
                else
                    FinalTeamClasament.put(clasament.getKey(),clasament.getValue());
            }
        }
        String toSend=new String();
        for(SortedMap.Entry<String,Integer> element:FinalTeamClasament.entrySet())
        {
            toSend+=element.getKey()+';'+element.getValue().toString()+';';
        }
        return toSend;
    }
}
