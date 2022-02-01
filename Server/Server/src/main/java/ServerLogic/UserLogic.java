package ServerLogic;

import database.dao.ParticipateDao;
import database.dao.TeamDao;
import database.dao.UserDao;
import database.model.ParticipateEntity;
import database.model.UserEntity;
import database.model.util.RoleConverter;
import org.postgresql.util.PSQLException;

import java.util.List;

public class UserLogic {

    private static UserDao userDao = new UserDao();
    private static TeamDao teamDao = new TeamDao();
    private static ParticipateDao participateDao=new ParticipateDao();

    static public String HandleLoginRequest(String username, String password) {
        String response = userDao.FindByUserAndPass(username, password);
        if(!participateDao.getAll().isEmpty())
            response+=";COMPLETE";
        else response+=";NOT COMPLETED";
        return response;
    }

    static public String GetListOfUsers() {
        List<UserEntity> users = userDao.getAll();
        String response = new String();
        for(UserEntity user : users) {
            if(user.getRole().toString().equals("PARTICIPANT"))
                response += user.getUsername()+';' + teamDao.get(user.getIdTeam()).getNameTeam()+';';
        }
        if(response.isEmpty())
            response="NONE";
        return response;
    }

    static public String UpdateNewUser(String username,String password,String fname,String lname, String Role,String Team) {
        RoleConverter roleConverter = new RoleConverter();
        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setFirstName(fname);
        newUser.setSecondName(lname);
        newUser.setRole(roleConverter.convertToEntityAttribute(Role));
        newUser.setIdTeam(teamDao.GetTeamIdFromName(Team));
        if(userDao.FindByUser(username).isEmpty()) {
            userDao.create(newUser);
            return "ADDED";
        }
        else return "NOT ADDED";
    }

    static public String DeleteUser(String username) {
          userDao.delete(username);
          return "DELETED";
    }
    static public String UpdateUser(String initialUsername, String username, String team) {
       userDao.update(initialUsername, username, teamDao.GetTeamIdFromName(team));
       return "UPDATED";
    }

    static public int GetNumberOfParticipants()
    {
        List<UserEntity> users=userDao.getAll();
        int result=0;
        for(UserEntity user:users)
        {
            if(user.getRole().toString().equals("PARTICIPANT"))
                result++;
        }
        return result;
    }
}
