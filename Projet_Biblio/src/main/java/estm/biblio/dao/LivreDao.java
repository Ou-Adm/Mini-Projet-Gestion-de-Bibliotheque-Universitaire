package estm.biblio.dao;

import estm.biblio.model.Livre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDao {
    public List<Livre> findAll() {
        List<Livre> liste = new ArrayList<>();
        try {
            ResultSet rs = DBConnection.getConn().createStatement().executeQuery("SELECT * FROM livre");
            while (rs.next()) {
                liste.add(new Livre(
                        rs.getString("isbn"), rs.getString("titre"),
                        rs.getString("auteur"), rs.getString("categorie"),
                        rs.getInt("nbr_exemplaires")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public boolean exists(String isbn) {
        try {
            String sql = "SELECT 1 FROM livre WHERE isbn = ?";
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setString(1, isbn);
            return ps.executeQuery().next();
        } catch (SQLException e) { return false; }
    }

    public void save(Livre l) {
        String sql = "INSERT INTO livre VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setString(1, l.getIsbn());
            ps.setString(2, l.getTitre());
            ps.setString(3, l.getAuteur());
            ps.setString(4, l.getCategorie());
            ps.setInt(5, l.getStock());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(String isbn) {
        try {
            String sql = "DELETE FROM livre WHERE isbn = ?";
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setString(1, isbn);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public int getStock(String isbn) {
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement("SELECT nbr_exemplaires FROM livre WHERE isbn = ?");
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public void updateStock(String isbn, int variation) {
        try {
            String sql = "UPDATE livre SET nbr_exemplaires = nbr_exemplaires + ? WHERE isbn = ?";
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setInt(1, variation);
            ps.setString(2, isbn);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}