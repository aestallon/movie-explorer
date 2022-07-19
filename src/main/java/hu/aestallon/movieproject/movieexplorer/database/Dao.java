package hu.aestallon.movieproject.movieexplorer.database;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    List<T> selectAll();
    Optional<T> select(Integer id);
    int delete(T t);
    int update(T t);
    int insert(T t);
}
