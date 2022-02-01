package database;

import jakarta.persistence.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.postgresql.util.PSQLException;

import java.util.function.Consumer;

public class DatabaseConnection {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public DatabaseConnection(){
        this.initTransaction();
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }

    public boolean executeTransaction(Consumer<EntityManager> action) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            action.accept(entityManager);
            entityTransaction.commit();
        } catch (RuntimeException e){
            System.err.println("Transaction error: "+ e.getLocalizedMessage());
            entityTransaction.rollback();
            return false;
        }
        return true;
    }

    public boolean initTransaction(){
        try{
            entityManagerFactory = Persistence.createEntityManagerFactory("CompetitionPersistence");
            entityManager = entityManagerFactory.createEntityManager();
        } catch(Exception e){
            System.err.println("Error at initilizing DatabaseManager: "+ e.getMessage());
            return false;
        }
        return true;
    }
};
