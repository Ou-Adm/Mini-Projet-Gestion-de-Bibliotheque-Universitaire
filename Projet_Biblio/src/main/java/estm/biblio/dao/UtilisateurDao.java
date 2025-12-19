package estm.biblio.dao;

import estm.biblio.model.Utilisateur;
import java.sql.*;

public class UtilisateurDao {

    // 1. Authentification
    public Utilisateur login(String login, String password) {
        String sql = "SELECT * FROM utilisateur WHERE login = ? AND password = ?";
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Utilisateur(rs.getString("login"), rs.getString("role"), rs.getInt("adherent_id"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // 2. Créer un nouvel utilisateur (Admin ou User)
    public void save(String login, String password, String role, int adherentId) {
        String sql = "INSERT INTO utilisateur (login, password, role, adherent_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ps.setString(3, role);

            // Si c'est un étudiant, on met son ID, sinon NULL
            if (adherentId > 0) {
                ps.setInt(4, adherentId);
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 3. Vérifier si le login existe déjà
    public boolean exists(String login) {
        try {
            String sql = "SELECT 1 FROM utilisateur WHERE login = ?";
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setString(1, login);
            return ps.executeQuery().next();
        } catch (SQLException e) { return false; }
    }
}