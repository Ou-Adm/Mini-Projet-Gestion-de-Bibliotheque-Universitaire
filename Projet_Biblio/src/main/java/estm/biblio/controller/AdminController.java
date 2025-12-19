package estm.biblio.controller;

import estm.biblio.dao.*;
import estm.biblio.model.*;
import estm.biblio.view.AdminDashboard;
import estm.biblio.view.LoginView;
import javax.swing.*;
import java.sql.ResultSet;

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

        // --- GESTION LIVRES ---
        v.addAddLivreListener(e -> {
            Livre l = v.getLivreForm();
            if(l!=null && !l.getIsbn().isEmpty()) {
                if(lDao.exists(l.getIsbn())) JOptionPane.showMessageDialog(v, "Cet ISBN existe déjà !");
                else { lDao.save(l); v.clearLivreForm(); load(); JOptionPane.showMessageDialog(v, "Livre ajouté."); }
            } else JOptionPane.showMessageDialog(v, "Erreur : Vérifiez les champs (Stock doit être un nombre).");
        });

        v.addDelLivreListener(e -> {
            String isbn = v.getSelectedIsbn();
            if(isbn!=null && JOptionPane.showConfirmDialog(v,"Supprimer ce livre ?")==0) { lDao.delete(isbn); load(); }
        });

        // --- GESTION COMPTES UNIFIÉE ---
        v.addCreateAccountListener(e -> {
            Adherent a = v.getAdherentData(); // Récupère Nom, Prénom, Email
            String login = v.getLogin();
            String pass = v.getPass();
            String role = v.getRole();

            // Vérif champs
            if(a.getNom().isEmpty() || login.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(v, "Tous les champs sont obligatoires.");
                return;
            }
            // Vérif login unique
            if(uDao.exists(login)) {
                JOptionPane.showMessageDialog(v, "Ce Login est déjà pris !");
                return;
            }

            // 1. On crée d'abord la fiche Identité (Adhérent)
            int idGenere = aDao.save(a);

            // 2. On crée le compte de connexion lié à cette identité
            if(idGenere > 0) {
                uDao.save(login, pass, role, idGenere);
                JOptionPane.showMessageDialog(v, "Compte " + role + " créé avec succès pour " + a.getNom());
                v.clearAccountForm();
                load();
            } else {
                JOptionPane.showMessageDialog(v, "Erreur base de données.");
            }
        });

        v.addDelAccountListener(e -> {
            int id = v.getSelectedAccountId();
            if(id > 0 && JOptionPane.showConfirmDialog(v,"Supprimer ce compte et ses données ?")==0) {
                // On supprime l'adhérent (la DAO s'occupe de supprimer l'user lié)
                aDao.delete(id);
                load();
            }
        });

        // --- LOGOUT  ---
        v.addLogoutListener(e -> {
            v.dispose();
            // 1. Créer la vue
            LoginView loginView = new LoginView();
            // 2. Lui attacher le contrôleur (Vital pour que le bouton marche)
            new LoginController(loginView);
            // 3. L'afficher
            loginView.setVisible(true);
        });
    }

    private void load() {
        // 1. Charger Emprunts
        eDao.chargerTousLesEmprunts(v.modelEmprunts);

        // 2. Charger Livres
        v.modelLivres.setRowCount(0);
        for(Livre l : lDao.findAll()) {
            v.modelLivres.addRow(new Object[]{l.getIsbn(), l.getTitre(), l.getAuteur(), l.getCategorie(), l.getStock()});
        }

        // 3. Charger Comptes
        v.modelComptes.setRowCount(0);
        try {
            String sql = "SELECT a.id, a.nom, a.prenom, a.email, u.login, u.role FROM adherent a JOIN utilisateur u ON u.adherent_id = a.id";
            ResultSet rs = DBConnection.getConn().createStatement().executeQuery(sql);
            while(rs.next()) {
                v.modelComptes.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nom") + " " + rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("login"),
                        rs.getString("role")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}