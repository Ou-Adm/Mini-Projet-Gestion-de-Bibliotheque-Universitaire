package estm.biblio.controller;

import estm.biblio.dao.*;
import estm.biblio.model.*;
import estm.biblio.service.BibliothequeService;
import estm.biblio.view.LoginView;
import estm.biblio.view.UserDashboard;
import javax.swing.JOptionPane;

public class UserController {
    private UserDashboard v;
    private Utilisateur u;
    private LivreDao lDao = new LivreDao();
    private EmpruntDao eDao = new EmpruntDao();
    private BibliothequeService service = new BibliothequeService();

    public UserController(UserDashboard v, Utilisateur u) {
        this.v = v;
        this.u = u;

        load(); // Chargement initial des données

        // --- ACTION : EMPRUNTER ---
        v.addEmprunterListener(e -> {
            String isbn = v.getSelectedIsbn();

            // 1. Vérif sélection
            if (isbn == null) {
                JOptionPane.showMessageDialog(v, "Veuillez sélectionner un livre dans la liste.");
                return;
            }

            // 2. Vérif sécurité (L'utilisateur doit être un Adhérent valide)
            if (u.getAdherentId() <= 0) {
                JOptionPane.showMessageDialog(v, "Erreur critique : Compte non lié à un dossier étudiant.");
                return;
            }

            // 3. Confirmation
            int confirm = JOptionPane.showConfirmDialog(v, "Confirmer l'emprunt ?", "Emprunt", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // 4. Appel Service (Règles métier : Stock, Retard, Max 3)
                String resultat = service.emprunterLivre(isbn, u.getAdherentId());
                JOptionPane.showMessageDialog(v, resultat);
                load(); // Rafraîchir
            }
        });

        // --- ACTION : LOGOUT ---
        v.addLogoutListener(e -> {
            v.dispose();
            LoginView lv = new LoginView();
            new LoginController(lv);
            lv.setVisible(true);
        });

        v.setVisible(true);
    }

    private void load() {
        v.modelLivres.setRowCount(0);
        v.modelMesEmprunts.setRowCount(0);

        for (Livre l : lDao.findAll()) {
            String dispo = (l.getStock() > 0) ? "Disponible" : "Indisponible (Rupture)";
            v.modelLivres.addRow(new Object[]{
                    l.getIsbn(),
                    l.getTitre(),
                    l.getAuteur(),
                    l.getCategorie(),
                    dispo
            });
        }

        if (u.getAdherentId() > 0) {
            eDao.chargerMesEmprunts(u.getAdherentId(), v.modelMesEmprunts);
        }
    }
}