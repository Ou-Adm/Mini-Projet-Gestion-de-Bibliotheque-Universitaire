package estm.biblio.controller;

import estm.biblio.dao.*;
import estm.biblio.model.*;
import estm.biblio.service.BibliothequeService;
import estm.biblio.view.LoginView;
import estm.biblio.view.UserDashboard;
import javax.swing.JOptionPane;
import java.util.List;

public class UserController {
    private UserDashboard v;
    private Utilisateur u;
    private LivreDao lDao = new LivreDao();
    private EmpruntDao eDao = new EmpruntDao();
    private BibliothequeService service = new BibliothequeService();

    public UserController(UserDashboard v, Utilisateur u) {
        this.v = v;
        this.u = u;

        load();

        v.addSearchListener(e -> {
            String keyword = v.getSearchText();
            List<Livre> resultats;

            if (keyword.trim().isEmpty()) {

                resultats = lDao.findAll();
            } else {
                resultats = lDao.search(keyword);
            }

            updateTableLivres(resultats);
        });

        // --- ACTION : EMPRUNTER ---
        v.addEmprunterListener(e -> {
            String isbn = v.getSelectedIsbn();

            if (isbn == null) {
                JOptionPane.showMessageDialog(v, "Veuillez sélectionner un livre dans la liste.");
                return;
            }

            if (u.getAdherentId() <= 0) {
                JOptionPane.showMessageDialog(v, "Erreur critique : Compte non lié à un dossier étudiant.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(v, "Confirmer l'emprunt ?", "Emprunt", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String resultat = service.emprunterLivre(isbn, u.getAdherentId());
                JOptionPane.showMessageDialog(v, resultat);
                load(); // Rafraîchir tout (stock baissé, nouvel emprunt ajouté)
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

        updateTableLivres(lDao.findAll());


        if (u.getAdherentId() > 0) {
            eDao.chargerMesEmprunts(u.getAdherentId(), v.modelMesEmprunts);
        }
    }

    private void updateTableLivres(List<Livre> livres) {
        v.modelLivres.setRowCount(0);
        for (Livre l : livres) {
            String dispo = (l.getStock() > 0) ? "Disponible (" + l.getStock() + ")" : "Indisponible";
            v.modelLivres.addRow(new Object[]{
                    l.getIsbn(),
                    l.getTitre(),
                    l.getAuteur(),
                    l.getCategorie(),
                    dispo
            });
        }
    }
}