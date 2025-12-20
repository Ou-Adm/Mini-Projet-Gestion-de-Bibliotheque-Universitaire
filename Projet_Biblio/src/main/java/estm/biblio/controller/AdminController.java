package estm.biblio.controller;

import estm.biblio.dao.*;
import estm.biblio.model.*;
import estm.biblio.view.AdminDashboard;
import estm.biblio.view.LoginView;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.List;

public class AdminController {
    private AdminDashboard v;
    private LivreDao lDao = new LivreDao();
    private AdherentDao aDao = new AdherentDao();
    private EmpruntDao eDao = new EmpruntDao();
    private UtilisateurDao uDao = new UtilisateurDao();

    public AdminController(AdminDashboard v) {
        this.v = v;
        load();
        v.setVisible(true);

        // ==================================================
        // 1. GESTION DES PRÊTS (Retourner)
        // ==================================================
        v.addRetourListener(e -> {
            int idEmprunt = v.getSelectedEmpruntId();
            String isbn = v.getSelectedEmpruntIsbn();

            if (idEmprunt != -1 && isbn != null) {
                if (JOptionPane.showConfirmDialog(v, "Confirmer le retour du livre ?") == 0) {

                    eDao.retourner(idEmprunt);
                    lDao.updateStock(isbn, 1);

                    JOptionPane.showMessageDialog(v, "Livre retourné avec succès.");
                    load(); // Rafraîchir
                }
            } else {
                JOptionPane.showMessageDialog(v, "Veuillez sélectionner une ligne d'emprunt.");
            }
        });

        // ==================================================
        // 2. GESTION DES LIVRES (Ajout, Modif, Recherche)
        // ==================================================

        // AJOUTER
        v.addAddLivreListener(e -> {
            Livre l = v.getLivreForm();
            if(l!=null && !l.getIsbn().isEmpty()) {
                if(lDao.exists(l.getIsbn())) JOptionPane.showMessageDialog(v, "Cet ISBN existe déjà !");
                else { lDao.save(l); v.clearLivreForm(); load(); JOptionPane.showMessageDialog(v, "Livre ajouté."); }
            } else JOptionPane.showMessageDialog(v, "Erreur formattage (Stock doit être un entier).");
        });

        // MODIFIER
        v.addUpdLivreListener(e -> {
            Livre l = v.getLivreForm(); // Récupère les données des champs
            if(l != null) {
                if(JOptionPane.showConfirmDialog(v, "Modifier le livre " + l.getIsbn() + " ?") == 0) {
                    lDao.update(l);
                    v.clearLivreForm();
                    load();
                }
            }
        });

        v.addTableLivreMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = v.tableLivres.getSelectedRow();
                String isbn = (String)v.tableLivres.getValueAt(row, 0);
                String titre = (String)v.tableLivres.getValueAt(row, 1);
                String auteur = (String)v.tableLivres.getValueAt(row, 2);
                String cat = (String)v.tableLivres.getValueAt(row, 3);
                int stock = (int)v.tableLivres.getValueAt(row, 4);
                v.setLivreForm(isbn, titre, auteur, cat, stock);
            }
        });


        v.addSearchListener(e -> {
            String keyword = v.getSearchText();
            List<Livre> resultats;
            if(keyword.isEmpty()) {
                resultats = lDao.findAll();
            } else {
                resultats = lDao.search(keyword);
            }

            v.modelLivres.setRowCount(0);
            for(Livre l : resultats) {
                v.modelLivres.addRow(new Object[]{l.getIsbn(), l.getTitre(), l.getAuteur(), l.getCategorie(), l.getStock()});
            }
        });

        v.addDelLivreListener(e -> {
            String isbn = v.getSelectedIsbn();
            if(isbn!=null && JOptionPane.showConfirmDialog(v,"Supprimer ce livre ?")==0) { lDao.delete(isbn); load(); }
        });

        v.addCreateAccountListener(e -> {
            Adherent a = v.getAdherentData();
            String login = v.getLogin(); String pass = v.getPass(); String role = v.getRole();
            if(a.getNom().isEmpty() || login.isEmpty()) return;
            if(uDao.exists(login)) { JOptionPane.showMessageDialog(v, "Login pris !"); return; }

            int id = aDao.save(a);
            if(id > 0) { uDao.save(login, pass, role, id); v.clearAccountForm(); load(); }
        });

        v.addUpdAccountListener(e -> {
            int id = v.getSelectedAccountId();
            if(id != -1) {
                Adherent a = v.getAdherentData();
                if(JOptionPane.showConfirmDialog(v, "Mettre à jour l'adhérent ID " + id + " ?") == 0) {
                    aDao.update(id, a.getNom(), a.getPrenom(), a.getEmail());
                    v.clearAccountForm();
                    load();
                }
            } else {
                JOptionPane.showMessageDialog(v, "Sélectionnez un compte.");
            }
        });

        v.addTableAccountMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = v.tableComptes.getSelectedRow();
                String nomComplet = (String)v.tableComptes.getValueAt(row, 1);
                String email = (String)v.tableComptes.getValueAt(row, 2);
                String login = (String)v.tableComptes.getValueAt(row, 3);
                String role = (String)v.tableComptes.getValueAt(row, 4);

                String[] names = nomComplet.split(" ", 2); // Séparer Nom Prénom
                String nom = names[0];
                String prenom = (names.length > 1) ? names[1] : "";

                v.setAccountForm(nom, prenom, email, login, role);
            }
        });

        v.addDelAccountListener(e -> {
            int id = v.getSelectedAccountId();
            if(id > 0 && JOptionPane.showConfirmDialog(v,"Supprimer ?")==0) { aDao.delete(id); load(); }
        });

        v.addLogoutListener(e -> {
            v.dispose();
            LoginView lv = new LoginView(); new LoginController(lv); lv.setVisible(true);
        });
    }

    private void load() {
        eDao.chargerTousLesEmprunts(v.modelEmprunts); // Charge aussi les ID

        v.modelLivres.setRowCount(0);
        for(Livre l : lDao.findAll()) {
            v.modelLivres.addRow(new Object[]{l.getIsbn(), l.getTitre(), l.getAuteur(), l.getCategorie(), l.getStock()});
        }

        v.modelComptes.setRowCount(0);
        try {
            String sql = "SELECT a.id, a.nom, a.prenom, a.email, u.login, u.role FROM adherent a JOIN utilisateur u ON u.adherent_id = a.id";
            ResultSet rs = DBConnection.getConn().createStatement().executeQuery(sql);
            while(rs.next()) {
                v.modelComptes.addRow(new Object[]{rs.getInt("id"), rs.getString("nom") + " " + rs.getString("prenom"), rs.getString("email"), rs.getString("login"), rs.getString("role")});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}