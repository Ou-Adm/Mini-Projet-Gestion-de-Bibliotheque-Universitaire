package estm.biblio.dao;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EmpruntDao {

    public boolean aDesRetardsGraves(int adherentId) {
        String sql = "SELECT COUNT(*) FROM emprunt WHERE adherent_id = ? AND date_retour_effective IS NULL AND date_retour_prevue < DATE_SUB(CURRENT_DATE, INTERVAL 10 DAY)";
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setInt(1, adherentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public int countActive(int adherentId) {
        String sql = "SELECT COUNT(*) FROM emprunt WHERE adherent_id = ? AND date_retour_effective IS NULL";
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setInt(1, adherentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public void create(String isbn, int adherentId) {
        String sql = "INSERT INTO emprunt (livre_isbn, adherent_id, date_emprunt, date_retour_prevue) VALUES (?, ?, CURRENT_DATE, DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY))";
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setString(1, isbn);
            ps.setInt(2, adherentId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void chargerMesEmprunts(int adherentId, DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT l.titre, e.date_emprunt, e.date_retour_prevue FROM emprunt e JOIN livre l ON e.livre_isbn = l.isbn WHERE e.adherent_id = ? AND e.date_retour_effective IS NULL";
        try {
            PreparedStatement ps = DBConnection.getConn().prepareStatement(sql);
            ps.setInt(1, adherentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("titre"),
                        rs.getDate("date_emprunt"),
                        rs.getDate("date_retour_prevue")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void chargerTousLesEmprunts(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT a.nom, a.prenom, l.titre, e.date_emprunt, e.date_retour_prevue " +
                "FROM emprunt e " +
                "JOIN livre l ON e.livre_isbn = l.isbn " +
                "JOIN adherent a ON e.adherent_id = a.id " +
                "WHERE e.date_retour_effective IS NULL " +
                "ORDER BY e.date_retour_prevue ASC";

        try {
            Statement stmt = DBConnection.getConn().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String etudiant = rs.getString("nom") + " " + rs.getString("prenom");
                String livre = rs.getString("titre");
                Date dateEmp = rs.getDate("date_emprunt");
                Date dateRetour = rs.getDate("date_retour_prevue");

                // Calcul mathématique des jours
                long joursRestants = ChronoUnit.DAYS.between(LocalDate.now(), dateRetour.toLocalDate());

                String statut;
                if (joursRestants < 0) {
                    statut = "RETARD DE " + Math.abs(joursRestants) + " Jours !";
                } else if (joursRestants == 0) {
                    statut = "A rendre AUJOURD'HUI";
                } else {
                    statut = joursRestants + " Jours restants";
                }

                model.addRow(new Object[]{etudiant, livre, dateEmp, dateRetour, statut});
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}