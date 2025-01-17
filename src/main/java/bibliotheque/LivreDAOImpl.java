package bibliotheque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAOImpl implements LivreDAO {
    @Override
    public Livre get(int id) throws SQLException {
        Connection con = Dbconnection.getConnection();
        Livre livre = null;
        PreparedStatement ps = null;
        try{
            String sql = "SELECT id, titre, auteur, categorie, nbexemplaires FROM livre WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int oid = rs.getInt("id");
                String titre = rs.getString("titre");
                String auteur = rs.getString("auteur");
                String categorie = rs.getString("categorie");
                int nbexemplaires = rs.getInt("nbexemplaires");

                livre = new Livre(oid, titre, auteur, categorie, nbexemplaires);
            }
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }

        return livre;
    }

    @Override
    public List<Livre> getAll() throws SQLException {
        List<Livre> livres = new ArrayList<>();
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "SELECT * FROM livre";
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String titre = rs.getString("titre");
                String auteur = rs.getString("auteur");
                String categorie = rs.getString("categorie");
                int nbexemplaires = rs.getInt("nbexemplaires");

                Livre livre = new Livre(id, titre, auteur, categorie, nbexemplaires);
                livres.add(livre);
            }

        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
        return livres;
    }

    @Override
    public int insert(Livre livre) throws SQLException {
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            if (livre.getAuteur() == null) {
                throw new IllegalArgumentException("L'auteur ne peut pas être null");
            }

            String sql = "INSERT INTO livre (titre, auteur, categorie, nbexemplaires) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setString(3, livre.getCategorie());
            ps.setInt(4, livre.getNbexemplaires());
            int result = ps.executeUpdate();
            return result;
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
    }

    @Override
    public int update(Livre livre) throws SQLException {
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE livre SET titre = ?, auteur = ?, categorie = ?, nbexemplaires = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setString(3, livre.getCategorie());
            ps.setInt(4, livre.getNbexemplaires());
            ps.setInt(5, livre.getId());
            int result = ps.executeUpdate();
            return result;
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
    }

    @Override
    public int delete(Livre livre) throws SQLException {
        Connection con = Dbconnection.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM livre WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, livre.getId());
            int result = ps.executeUpdate();
            return result;
        } finally {
            Dbconnection.closePreparedStatement(ps);
            Dbconnection.closeConnection(con);
        }
    }
}
