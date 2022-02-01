package database.dao;

import org.postgresql.util.PSQLException;

import java.util.List;

public interface DaoI <T>{
    T get(long id);
    List<T> getAll();
    void create(T t) ;
}
