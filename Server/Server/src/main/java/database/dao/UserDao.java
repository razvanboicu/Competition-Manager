package database.dao;

import database.DatabaseConnection;
import database.model.UserEntity;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.postgresql.util.PSQLException;

import java.sql.ResultSet;
import java.util.List;

public class UserDao implements DaoI<UserEntity>{
    DatabaseConnection connection = new DatabaseConnection();


    @Override
    public UserEntity get(long id) {
        return connection.getEntityManager().find(UserEntity.class, id);
    }

public String FindByUserAndPass(String user, String Password){

    String verifyLogin = "SELECT a FROM UserEntity a WHERE a.username = '"+ user +"' AND a.password = '"+ Password +"'";
    TypedQuery<UserEntity> query = connection.getEntityManager().createQuery(verifyLogin,UserEntity.class);
    if(!query.getResultList().isEmpty())
    {
       return query.getResultList().get(0).getRole().toString();
    }
    return "NOT FOUND";
}

public List<UserEntity> FindByUser(String User)
{

    String findUser = "SELECT a FROM UserEntity a WHERE a.username = '"+ User +"'";
    TypedQuery<UserEntity> query = connection.getEntityManager().createQuery(findUser,UserEntity.class);
    return query.getResultList();
}

public List<UserEntity> FindByTeam(int Team)
{
    String findTeam = "SELECT a FROM UserEntity a WHERE a.idTeam = '"+ Team +"'";
    TypedQuery<UserEntity> query = connection.getEntityManager().createQuery(findTeam,UserEntity.class);
   return query.getResultList();
}

    @Override
    public List<UserEntity> getAll() {
        TypedQuery<UserEntity> query = connection.getEntityManager().createQuery("SELECT a FROM UserEntity a", UserEntity.class);
        return  query.getResultList();
    }

    @Override
    public void create(UserEntity userEntity)  {
        connection.executeTransaction(entityManager -> entityManager.persist(userEntity));
    }

    public void delete(String username) {
        connection.executeTransaction(entityManager -> {
            Query query=  connection.getEntityManager().createQuery("DELETE FROM UserEntity a WHERE a.username='"+username+"'");
            query.executeUpdate();
        });
    }

    public void update(String initialUsername,String usernamee,int team)
    {
        connection.executeTransaction(entityManager -> {
            Query query = connection.getEntityManager().createQuery(
                    "UPDATE UserEntity SET username='"+usernamee+"', idTeam='"+team+"'WHERE username='"+initialUsername+"'");
            query.executeUpdate();
        });
    }

}
