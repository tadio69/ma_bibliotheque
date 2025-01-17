package bibliotheque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreDAOImpl implements MembreDAO{
    @Override
    public Membre get(int id) throws SQLException {
        Connection con = Dbconnection.getConnection();
        Membre membre = null;
        PreparedStatement ps = null;
        try{
            String sql = "SELECT id, nom, prenom, email, adhesiondate FROM membre WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int oid = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                Date adhesiondate = rs.getDate("adhesiondate");

                membre = new Membre(oid, nom, prenom, email, adhesiondate);
            }
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }

        return membre;
    }

    @Override
    public List<Membre> getAll() throws SQLException {
        List<Membre> membres = new ArrayList<>();
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "SELECT * FROM membre";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                Date adhesiondate = rs.getDate("adhesiondate");

                Membre membre = new Membre(id, nom, prenom, email, adhesiondate);
                membres.add(membre);
            }

        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
        return membres;
    }

    @Override
    public int insert(Membre membre) throws SQLException {
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO membre (nom, prenom, email, adhesiondate) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, membre.getNom());
            ps.setString(2, membre.getPrenom());
            ps.setString(3, membre.getEmail());
            ps.setDate(4, membre.getAdhesiondate());
            int result = ps.executeUpdate();
            return result;
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
    }

    @Override
    public int update(Membre membre) throws SQLException {
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE membre SET nom = ?, prenom = ?, email = ?, adhesiondate = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, membre.getNom());
            ps.setString(2, membre.getPrenom());
            ps.setString(3, membre.getEmail());
            ps.setDate(4, membre.getAdhesiondate());
            ps.setInt(5, membre.getId());
            int result = ps.executeUpdate();
            return result;
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
    }

    @Override
    public int delete(Membre membre) throws SQLException {
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM membre WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, membre.getId());
            int result = ps.executeUpdate();
            return result;
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
    }
}
