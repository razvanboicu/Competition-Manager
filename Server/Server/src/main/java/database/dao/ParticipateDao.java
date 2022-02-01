package database.dao;

import database.DatabaseConnection;
import database.model.ParticipateEntity;
import database.model.StageEntity;
import jakarta.persistence.TypedQuery;
import org.postgresql.util.PSQLException;

import java.util.List;

public class ParticipateDao implements DaoI<ParticipateEntity> {
    DatabaseConnection connection = new DatabaseConnection();

    @Override
    public ParticipateEntity get(long id) {
        return connection.getEntityManager().find(ParticipateEntity.class, id);
    }

    @Override
    public List<ParticipateEntity> getAll() {
        TypedQuery<ParticipateEntity> query = connection.getEntityManager().createQuery("SELECT a FROM ParticipateEntity a", ParticipateEntity.class);
        return  query.getResultList();
    }
    public List<ParticipateEntity> FindByStageID(int StageID)
    {
        TypedQuery<ParticipateEntity> query=connection.getEntityManager().createQuery("SELECT a from ParticipateEntity  a WHERE a.idStage='"+ StageID+"'",ParticipateEntity.class);
        return query.getResultList();
    }

    public List<ParticipateEntity> FindByUserIDAndStageID(int UserID,int StageID)
    {
        TypedQuery<ParticipateEntity> query=connection.getEntityManager().createQuery("SELECT a from ParticipateEntity  a WHERE a.idUser='"+ UserID+"'AND a.idStage='"+StageID+"'",ParticipateEntity.class);
        return query.getResultList();
    }

    @Override
    public void create(ParticipateEntity participateEntity)  {
        connection.executeTransaction(entityManager -> entityManager.persist(participateEntity));
    }
}
