package estm.biblio.controller;

import estm.biblio.dao.UtilisateurDao;
import estm.biblio.model.Utilisateur;
import estm.biblio.view.*;
import javax.swing.JOptionPane;

public class LoginController {
    public LoginController(LoginView view) {
        view.addLoginListener(e -> {
            Utilisateur user = new UtilisateurDao().login(view.getUser(), view.getPass());
            if (user != null) {
                view.dispose();
                if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    new AdminController(new AdminDashboard());
                } else {
                    new UserController(new UserDashboard(user.getLogin()), user);
                }
            } else {
                JOptionPane.showMessageDialog(view, "Identifiants incorrects !");
            }
        });
    }
}