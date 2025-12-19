package estm.biblio;

import estm.biblio.view.LoginView;
import estm.biblio.controller.LoginController;

public class App {
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        LoginView v = new LoginView();
        new LoginController(v);
        v.setVisible(true);
    }
}