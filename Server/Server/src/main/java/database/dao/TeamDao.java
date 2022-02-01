package database.dao;

import database.DatabaseConnection;
import database.model.TeamEntity;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.postgresql.util.PSQLException;

import java.util.List;

public class TeamDao implements DaoI<TeamEntity> {
    DatabaseConnection connection = new DatabaseConnection();
    @Override
    public TeamEntity get(long id) {
        return connection.getEntityManager().find(TeamEntity.class, id);
    }

    @Override
    public List<TeamEntity> getAll() {
        TypedQuery<TeamEntity> query = connection.getEntityManager().createQuery("SELECT a FROM TeamEntity a", TeamEntity.class);
        return  query.getResultList();
    }

    public int GetTeamIdFromName(String teamname)
    {
        int result;
        TypedQuery<TeamEntity> query = connection.getEntityManager().createQuery("SELECT a FROM TeamEntity a WHERE a.nameTeam ='"+ teamname +"'",TeamEntity.class);
        result=(int)query.getResultList().get(0).getIdTeam();
        return result;
    }

    @Override
    public void create(TeamEntity teamEntity)  {
        connection.executeTransaction(entityManager -> entityManager.persist(teamEntity));
    }

    public void delete(String nameTeam){
        connection.executeTransaction(entityManager -> {
            Query query = connection.getEntityManager().createQuery("DELETE FROM TeamEntity a WHERE a.nameTeam = '"+ nameTeam +"'");
            query.executeUpdate();
        });
    }

    public void update(String initialTeamName,String nameTeam)
    {
        connection.executeTransaction(entityManager -> {
            Query query = connection.getEntityManager().createQuery(
                    "UPDATE TeamEntity SET nameTeam ='"+nameTeam+"'WHERE nameTeam='"+initialTeamName+"'");
            query.executeUpdate();
        });
    }
}
