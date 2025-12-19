package estm.biblio.dao;

import estm.biblio.model.Adherent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdherentDao {

    public List<Adherent> findAll() {
        List<Adherent> liste = new ArrayList<>();
        try {
            ResultSet rs = DBConnection.getConn().createStatement().executeQuery("SELECT * FROM adherent");
            while (rs.next()) {
                liste.add(new Adherent(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("statut")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }


    public int save(Adherent a) {
        String sql = "INSERT INTO adherent (nom, prenom, email, statut) VALUES (?, ?, ?, 'ACTIF')";
        int newId = 0;
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, a.getNom());
            ps.setString(2, a.getPrenom());
            ps.setString(3, a.getEmail());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) newId = rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return newId;
    }

    public void delete(int id) {
        try {
            PreparedStatement psUser = DBConnection.getConn().prepareStatement("DELETE FROM utilisateur WHERE adherent_id=?");
            psUser.setInt(1, id);
            psUser.executeUpdate();

            PreparedStatement psAdh = DBConnection.getConn().prepareStatement("DELETE FROM adherent WHERE id=?");
            psAdh.setInt(1, id);
            psAdh.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}