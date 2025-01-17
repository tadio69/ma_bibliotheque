package bibliotheque;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

interface DAO<T> {
    // () =
    T get(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    int insert(T t) throws SQLException;

    int update(T t) throws SQLException;

    int delete(T t) throws SQLException;
}
