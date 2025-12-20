package estm.biblio.dao;

import estm.biblio.model.Livre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDao {

    // RECHERCHE (Par titre, auteur ou ISBN)
    public List<Livre> search(String keyword) {
        List<Livre> liste = new ArrayList<>();
        String sql = "SELECT * FROM livre WHERE titre LIKE ? OR auteur LIKE ? OR isbn LIKE ?";
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new Livre(rs.getString("isbn"), rs.getString("titre"), rs.getString("auteur"), rs.getString("categorie"), rs.getInt("nbr_exemplaires")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    // MODIFICATION
    public void update(Livre l) {
        String sql = "UPDATE livre SET titre=?, auteur=?, categorie=?, nbr_exemplaires=? WHERE isbn=?";
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setString(1, l.getTitre());
            ps.setString(2, l.getAuteur());
            ps.setString(3, l.getCategorie());
            ps.setInt(4, l.getStock());
            ps.setString(5, l.getIsbn());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // --- Méthodes existantes ---
    public List<Livre> findAll() {
        List<Livre> liste = new ArrayList<>();
        try {
            ResultSet rs = DBConnection.getConn().createStatement().executeQuery("SELECT * FROM livre");
            while (rs.next()) {
                liste.add(new Livre(rs.getString("isbn"), rs.getString("titre"), rs.getString("auteur"), rs.getString("categorie"), rs.getInt("nbr_exemplaires")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    public boolean exists(String isbn) {
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement("SELECT 1 FROM livre WHERE isbn = ?");
            ps.setString(1, isbn);
            return ps.executeQuery().next();
        } catch (SQLException e) { return false; }
    }

    public void save(Livre l) {
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement("INSERT INTO livre VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, l.getIsbn()); ps.setString(2, l.getTitre()); ps.setString(3, l.getAuteur()); ps.setString(4, l.getCategorie()); ps.setInt(5, l.getStock());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(String isbn) {
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement("DELETE FROM livre WHERE isbn = ?");
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
            PreparedStatement ps = DBConnection.getConn().prepareStatement("UPDATE livre SET nbr_exemplaires = nbr_exemplaires + ? WHERE isbn = ?");
            ps.setInt(1, variation); ps.setString(2, isbn);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}