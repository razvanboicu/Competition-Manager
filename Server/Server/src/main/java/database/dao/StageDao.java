package database.dao;

import database.DatabaseConnection;
import database.model.StageEntity;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.postgresql.util.PSQLException;

import java.lang.reflect.Type;
import java.util.List;

public class StageDao implements DaoI<StageEntity> {
    DatabaseConnection connection = new DatabaseConnection();

    @Override
    public StageEntity get(long id) {
        return connection.getEntityManager().find(StageEntity.class, id);
    }

    @Override
    public List<StageEntity> getAll() {
        TypedQuery<StageEntity> query = connection.getEntityManager().createQuery("SELECT a FROM StageEntity a", StageEntity.class);
        return  query.getResultList();
    }

    @Override
    public void create(StageEntity stageEntity)  {
        connection.executeTransaction(entityManager -> entityManager.persist(stageEntity));
    }
    public void delete(String stageName) {
        connection.executeTransaction(entityManager -> {
            Query query =  connection.getEntityManager().createQuery("DELETE FROM StageEntity a WHERE a.nameStage ='"+stageName+"'");
            query.executeUpdate();
        });
    }

    public void update(String initialStageName, String stageName) {
        connection.executeTransaction(entityManager -> {
            Query query = connection.getEntityManager().createQuery(
                    "UPDATE StageEntity SET nameStage='"+stageName+"'WHERE nameStage='"+initialStageName+"'");
            query.executeUpdate();
        });
    }

    public void updateStatus(boolean status, long StageID)
    {
        connection.executeTransaction(entityManager -> {
            Query query = connection.getEntityManager().createQuery("UPDATE StageEntity SET status='"+status+ "' WHERE idStage='"+StageID+"'");
            query.executeUpdate();
        });
    }
}
