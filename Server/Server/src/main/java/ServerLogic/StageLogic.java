package ServerLogic;

import database.dao.ParticipateDao;
import database.dao.StageDao;
import database.dao.UserDao;
import database.model.StageEntity;
import database.model.UserEntity;

import java.util.List;

public class StageLogic {
    private static StageDao stageDao = new StageDao();
    private static ParticipateDao participateDao=new ParticipateDao();
    private static UserDao userDao=new UserDao();

    static public String GetListOfStages(){
        List<StageEntity> stages = stageDao.getAll();
        String response = new String();
        for(StageEntity stage : stages) {
                response += stage.getNameStage()+';';
        }
        if(response.isEmpty())
            response="NONE";
        return response;
    }

    static public String UpdateNewStage(String StageName)
    {
        StageEntity newStage = new StageEntity();
        newStage.setNameStage(StageName);
        stageDao.create(newStage);
        return "ADDED";
    }

    static public String DeleteStage(String stageName)
    {
        stageDao.delete(stageName);
        return "DELETED";
    }

    static public String UpdateStage(String initialStageName,String stage) {
        stageDao.update(initialStageName, stage);
        return "UPDATED";
    }

    static public String GetCurrentStage()
    {
        String currentStage;
        StageDao update=new StageDao();
        List<StageEntity> stages=update.getAll();
        for(StageEntity stage:stages)
        {
            if(!stage.isStatus()) {
                currentStage = String.valueOf(stage.getIdStage());
                return currentStage;
            }
        }
        return String.valueOf(stages.get(stages.size()-1).getIdStage());
    }

    static public String GetCurrentStageInfo(int currentStage,String username)
    {
        String toSend;
        toSend=stageDao.get(currentStage).getNameStage()+";";
        if(!participateDao.FindByUserIDAndStageID((int)userDao.FindByUser(username).get(0).getIdUser(),currentStage).isEmpty())
        toSend+=participateDao.FindByUserIDAndStageID((int)userDao.FindByUser(username).get(0).getIdUser(),currentStage).get(0).getScore();
        else toSend+="0";
        return toSend;
    }

    static public String GetNextStage(String stage)
    {
        String nextStage;
        List<StageEntity> stages=stageDao.getAll();
        for(int index=0;index<stages.size();index++) {
            if (stages.get(index).getIdStage() == Integer.valueOf(stage))
                if (index != stages.size() - 1)
                    return String.valueOf(stages.get(index + 1).getIdStage());
        }
        return "LAST STAGE";
    }

    static public String GetPreviousStage(String stage)
    {
        List<StageEntity> stages=stageDao.getAll();
        for(int index=0;index<stages.size();index++) {
            if (stages.get(index).getIdStage() == Integer.valueOf(stage))
                if (index != 0)
                    return String.valueOf(stages.get(index - 1).getIdStage());
        }
        return "FIRST STAGE";
    }
}
