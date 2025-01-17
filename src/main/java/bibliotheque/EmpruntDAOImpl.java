package bibliotheque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAOImpl implements EmpruntDAO {
    @Override
    public Emprunt get(int id) throws SQLException {
        Connection con = Dbconnection.getConnection();
        Emprunt emprunt = null;
        PreparedStatement ps = null;
        try{
            String sql = "SELECT id, membre_id, livre_id, date_emprunt, date_retour_prevue, date_retour_effective FROM emprunt WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int oid = rs.getInt("id");
                int membre_id = rs.getInt("membre_id");
                int livre_id = rs.getInt("livre_id");
                Date date_emprunt = rs.getDate("date_emprunt");
                Date date_retour_prevue = rs.getDate("date_retour_prevue");
                Date date_retour_effective = rs.getDate("date_retour_effective");

                emprunt = new Emprunt(oid, membre_id, livre_id, date_emprunt, date_retour_prevue, date_retour_effective);
            }
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }

        return emprunt;
    }

    @Override
    public List<Emprunt> getAll() throws SQLException {
        List<Emprunt> emprunts = new ArrayList<>();
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "SELECT * FROM emprunt";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int membre_id = rs.getInt("membre_id");
                int livre_id = rs.getInt("livre_id");
                Date date_emprunt = rs.getDate("date_emprunt");
                Date date_retour_prevue = rs.getDate("date_retour_prevue");
                Date date_retour_effective = rs.getDate("date_retour_effective");
                Emprunt emprunt = new Emprunt(id, membre_id, livre_id, date_emprunt, date_retour_prevue, date_retour_effective);
                emprunts.add(emprunt);
            }

        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
        return emprunts;
    }

    @Override
    public int insert(Emprunt emprunt) throws SQLException {
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO emprunt (membre_id, livre_id, date_emprunt, date_retour_prevue) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, emprunt.getMembre_id());
            ps.setInt(2, emprunt.getLivre_id());
            ps.setDate(3, emprunt.getDate_emprunt());
            ps.setDate(4, emprunt.getDate_retour_prevue());
            int result = ps.executeUpdate();
            return result;
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
    }

    @Override
    public int update(Emprunt emprunt) throws SQLException {
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE emprunt SET membre_id = ?, livre_id = ?, date_emprunt = ?, date_retour_prevue = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, emprunt.getMembre_id());
            ps.setInt(2, emprunt.getLivre_id());
            ps.setDate(3, emprunt.getDate_emprunt());
            ps.setDate(4, emprunt.getDate_retour_prevue());
            ps.setInt(5, emprunt.getId());
            int result = ps.executeUpdate();
            return result;
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
    }

    @Override
    public int delete(Emprunt emprunt) throws SQLException {
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM emprunt WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, emprunt.getId());
            int result = ps.executeUpdate();
            return result;
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
    }

    @Override
    public int enregistrerRetour(int empruntId, Date dateRetourEffective) throws SQLException {
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            if (dateRetourEffective.before(get(empruntId).getDate_retour_prevue())) {
                throw new IllegalArgumentException("La date de retour effective ne peut pas être antérieure à la date de retour prévue.");
            }
            String sql = "UPDATE emprunt SET date_retour_effective = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setDate(1, dateRetourEffective);
            ps.setInt(2, empruntId);
            int result = ps.executeUpdate();
            return result;
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
    }

    @Override
    public List<Emprunt> getEmpruntsEnRetard() throws SQLException {
        List<Emprunt> empruntsEnRetard = new ArrayList<>();
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "SELECT * FROM emprunt WHERE date_retour_effective > date_retour_prevue";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                empruntsEnRetard.add(createEmpruntFromResultSet(rs));
            }
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
        return empruntsEnRetard;
    }

    @Override
    public Emprunt createEmpruntFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int membre_id = rs.getInt("membre_id");
        int livre_id = rs.getInt("livre_id");
        Date date_emprunt = rs.getDate("date_emprunt");
        Date date_retour_prevue = rs.getDate("date_retour_prevue");
        Date date_retour_effective = rs.getDate("date_retour_effective");
        Emprunt emprunt = new Emprunt(id, membre_id, livre_id, date_emprunt, date_retour_prevue, date_retour_effective);
        return emprunt;
    }
}
