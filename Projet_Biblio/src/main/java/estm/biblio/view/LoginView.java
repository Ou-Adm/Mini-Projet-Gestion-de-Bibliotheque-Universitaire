package estm.biblio.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField txtUser = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JButton btnConnect = new JButton("SE CONNECTER");

    // Couleurs
    private final Color DARK_BG = new Color(45, 52, 54);       // Fond Gris Foncé
    private final Color TEXT_WHITE = Color.WHITE;
    private final Color BTN_BLUE = new Color(9, 132, 227);     // Bleu Vibrant
    private final Color INPUT_BG = new Color(223, 230, 233);   // Gris clair pour écrire

    public LoginView() {
        setTitle("Authentification");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(DARK_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // -- Labels --
        JLabel lblUser = new JLabel("Identifiant");
        lblUser.setForeground(TEXT_WHITE);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel lblPass = new JLabel("Mot de passe");
        lblPass.setForeground(TEXT_WHITE);
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // -- Inputs --
        txtUser.setBackground(INPUT_BG);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUser.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));

        txtPass.setBackground(INPUT_BG);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPass.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));

        // -- Bouton
        btnConnect.setBackground(BTN_BLUE);
        btnConnect.setForeground(Color.WHITE);
        btnConnect.setFocusPainted(false);
        btnConnect.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConnect.setBorderPainted(false); // IMPORTANT : Enlève le style Windows par défaut
        btnConnect.setOpaque(true);         // IMPORTANT : Force la couleur
        btnConnect.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConnect.setPreferredSize(new Dimension(200, 35));

        // -- Assemblage --
        gbc.gridx=0; gbc.gridy=0; gbc.anchor=GridBagConstraints.WEST;
        mainPanel.add(lblUser, gbc);

        gbc.gridx=0; gbc.gridy=1; gbc.fill=GridBagConstraints.HORIZONTAL;
        mainPanel.add(txtUser, gbc);

        gbc.gridx=0; gbc.gridy=2; gbc.fill=GridBagConstraints.NONE; gbc.anchor=GridBagConstraints.WEST;
        mainPanel.add(lblPass, gbc);

        gbc.gridx=0; gbc.gridy=3; gbc.fill=GridBagConstraints.HORIZONTAL;
        mainPanel.add(txtPass, gbc);

        gbc.gridx=0; gbc.gridy=4; gbc.fill=GridBagConstraints.NONE; gbc.anchor=GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        mainPanel.add(btnConnect, gbc);

        add(mainPanel);
    }

    public String getUser() { return txtUser.getText(); }
    public String getPass() { return new String(txtPass.getPassword()); }
    public void addLoginListener(ActionListener l) { btnConnect.addActionListener(l); }
}