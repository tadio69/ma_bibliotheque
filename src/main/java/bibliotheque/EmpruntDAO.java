package bibliotheque;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface EmpruntDAO extends DAO<Emprunt> {
    int enregistrerRetour(int id, Date date) throws SQLException;
    List<Emprunt> getEmpruntsEnRetard() throws SQLException;
    Emprunt createEmpruntFromResultSet(ResultSet rs) throws SQLException;
}
