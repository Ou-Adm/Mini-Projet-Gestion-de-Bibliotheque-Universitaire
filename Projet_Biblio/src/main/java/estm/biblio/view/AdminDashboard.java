package estm.biblio.view;

import estm.biblio.model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {


    private final Color DARK_BG = new Color(45, 52, 54);
    private final Color HEADER_BG = new Color(30, 30, 30);
    private final Color PANEL_BG = new Color(60, 67, 70);
    private final Color TEXT_WHITE = new Color(240, 240, 240);

    private final Color BTN_BLUE = new Color(9, 132, 227);
    private final Color BTN_GREEN = new Color(0, 184, 148);
    private final Color BTN_RED = new Color(214, 48, 49);

    private JButton btnLogout = new JButton("Se Déconnecter");

    // ONGLET 1
    public DefaultTableModel modelEmprunts = new DefaultTableModel(new String[]{"Emprunteur", "Livre", "Date Prêt", "Retour Prévu", "Statut"}, 0);
    private JTable tableEmprunts = new JTable(modelEmprunts);

    // ONGLET 2
    private JTextField txtIsbn = new JTextField(15);
    private JTextField txtTitre = new JTextField(15);
    private JTextField txtAuteur = new JTextField(15);
    private JComboBox<String> cbCategorie = new JComboBox<>(new String[]{"Informatique", "Mathématiques", "Physique", "Droit", "Roman", "Autre"});
    private JTextField txtStock = new JTextField(5);
    private JButton btnAddLivre = new JButton("Ajouter Livre");
    private JButton btnDelLivre = new JButton("Supprimer Sélection");
    public DefaultTableModel modelLivres = new DefaultTableModel(new String[]{"ISBN", "Titre", "Auteur", "Catégorie", "Stock"}, 0);
    private JTable tableLivres = new JTable(modelLivres);

    // ONGLET 3
    private JTextField txtNom = new JTextField(15);
    private JTextField txtPrenom = new JTextField(15);
    private JTextField txtEmail = new JTextField(15);
    private JTextField txtLogin = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JComboBox<String> cbRole = new JComboBox<>(new String[]{"USER (Adhérent)", "ADMIN"});
    private JButton btnCreateAccount = new JButton("Créer Compte");
    public DefaultTableModel modelComptes = new DefaultTableModel(new String[]{"ID", "Nom & Prénom", "Email", "Login", "Rôle"}, 0);
    private JTable tableComptes = new JTable(modelComptes);
    private JButton btnDelAccount = new JButton("Supprimer Compte");

    public AdminDashboard() {
        setTitle("Administration ");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(DARK_BG);

        // --- HEADER ---
        JPanel pHeader = new JPanel(new BorderLayout());
        pHeader.setBackground(HEADER_BG);
        pHeader.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel("DASHBOARD ADMIN");
        lblTitle.setForeground(TEXT_WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));

        styleButton(btnLogout, BTN_RED); // Bouton Rouge

        pHeader.add(lblTitle, BorderLayout.WEST);
        pHeader.add(btnLogout, BorderLayout.EAST);
        add(pHeader, BorderLayout.NORTH);

        // --- TABS ---
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // 1. PRÊTS
        JPanel p1 = new JPanel(new BorderLayout());
        p1.setBackground(DARK_BG);
        p1.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel l1 = new JLabel("Suivi des Emprunts en cours");
        l1.setForeground(TEXT_WHITE); l1.setFont(new Font("Segoe UI", Font.BOLD, 16)); l1.setBorder(new EmptyBorder(0,0,10,0));
        p1.add(l1, BorderLayout.NORTH);
        p1.add(new JScrollPane(tableEmprunts), BorderLayout.CENTER);

        // 2. LIVRES
        JPanel p2 = new JPanel(new BorderLayout());
        p2.setBackground(DARK_BG);

        JPanel p2Form = new JPanel(new GridBagLayout());
        p2Form.setBackground(PANEL_BG);
        p2Form.setBorder(createTitleBorder("Nouveau Livre"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8); gbc.anchor=GridBagConstraints.WEST; gbc.fill=GridBagConstraints.HORIZONTAL;

        addFormRow(p2Form, gbc, 0, "ISBN:", txtIsbn);
        addFormRow(p2Form, gbc, 1, "Titre:", txtTitre);
        addFormRow(p2Form, gbc, 2, "Auteur:", txtAuteur);
        addFormRow(p2Form, gbc, 3, "Catégorie:", cbCategorie);
        addFormRow(p2Form, gbc, 4, "Stock:", txtStock);

        gbc.gridx=0; gbc.gridy=5; gbc.gridwidth=2;
        styleButton(btnAddLivre, BTN_GREEN);
        p2Form.add(btnAddLivre, gbc);

        p2.add(p2Form, BorderLayout.WEST);
        p2.add(new JScrollPane(tableLivres), BorderLayout.CENTER);

        JPanel p2Bot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p2Bot.setBackground(DARK_BG);
        styleButton(btnDelLivre, BTN_RED);
        p2Bot.add(btnDelLivre);
        p2.add(p2Bot, BorderLayout.SOUTH);

        // 3. COMPTES
        JPanel p3 = new JPanel(new BorderLayout());
        p3.setBackground(DARK_BG);

        JPanel p3Form = new JPanel(new GridBagLayout());
        p3Form.setBackground(PANEL_BG);
        p3Form.setBorder(createTitleBorder("Création Compte & Adhérent"));

        // Ligne 1
        gbc.gridwidth=1; gbc.gridy=0;
        addFormRowH(p3Form, gbc, 0, "Nom:", txtNom);
        addFormRowH(p3Form, gbc, 2, "Prénom:", txtPrenom);
        addFormRowH(p3Form, gbc, 4, "Email:", txtEmail);
        // Ligne 2
        gbc.gridy=1;
        addFormRowH(p3Form, gbc, 0, "Login:", txtLogin);
        addFormRowH(p3Form, gbc, 2, "Pass:", txtPass);
        addFormRowH(p3Form, gbc, 4, "Rôle:", cbRole);

        gbc.gridx=6; styleButton(btnCreateAccount, BTN_BLUE);
        p3Form.add(btnCreateAccount, gbc);

        p3.add(p3Form, BorderLayout.NORTH);
        p3.add(new JScrollPane(tableComptes), BorderLayout.CENTER);

        JPanel p3Bot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p3Bot.setBackground(DARK_BG);
        styleButton(btnDelAccount, BTN_RED);
        p3Bot.add(btnDelAccount);
        p3.add(p3Bot, BorderLayout.SOUTH);

        tabs.addTab("Suivi Prêts", p1); tabs.addTab("Catalogue Livres", p2); tabs.addTab("Utilisateurs", p3);
        add(tabs, BorderLayout.CENTER);
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private TitledBorder createTitleBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), title);
        border.setTitleColor(TEXT_WHITE);
        return border;
    }

    private void addFormRow(JPanel p, GridBagConstraints gbc, int y, String lbl, JComponent f) {
        gbc.gridx=0; gbc.gridy=y; JLabel l=new JLabel(lbl); l.setForeground(TEXT_WHITE); p.add(l, gbc);
        gbc.gridx=1; f.setPreferredSize(new Dimension(150, 25)); p.add(f, gbc);
    }

    private void addFormRowH(JPanel p, GridBagConstraints gbc, int x, String lbl, JComponent f) {
        gbc.gridx=x; JLabel l=new JLabel(lbl); l.setForeground(TEXT_WHITE); p.add(l, gbc);
        gbc.gridx=x+1; f.setPreferredSize(new Dimension(120, 25)); p.add(f, gbc);
    }

    // Getters
    public void addLogoutListener(ActionListener l) { btnLogout.addActionListener(l); }
    public Livre getLivreForm() { try{ return new Livre(txtIsbn.getText(), txtTitre.getText(), txtAuteur.getText(), (String)cbCategorie.getSelectedItem(), Integer.parseInt(txtStock.getText())); } catch(Exception e){return null;} }
    public String getSelectedIsbn() { int r=tableLivres.getSelectedRow(); return r!=-1?(String)tableLivres.getValueAt(r,0):null; }
    public void addAddLivreListener(ActionListener l) { btnAddLivre.addActionListener(l); }
    public void addDelLivreListener(ActionListener l) { btnDelLivre.addActionListener(l); }
    public void clearLivreForm() { txtIsbn.setText(""); txtTitre.setText(""); txtAuteur.setText(""); txtStock.setText(""); }
    public Adherent getAdherentData() { return new Adherent(0, txtNom.getText(), txtPrenom.getText(), txtEmail.getText(), "ACTIF"); }
    public String getLogin() { return txtLogin.getText(); }
    public String getPass() { return new String(txtPass.getPassword()); }
    public String getRole() { return ((String)cbRole.getSelectedItem()).startsWith("ADMIN") ? "ADMIN" : "USER"; }
    public void addCreateAccountListener(ActionListener l) { btnCreateAccount.addActionListener(l); }
    public void addDelAccountListener(ActionListener l) { btnDelAccount.addActionListener(l); }
    public int getSelectedAccountId() { int r=tableComptes.getSelectedRow(); return r!=-1?Integer.parseInt(tableComptes.getValueAt(r,0).toString()):0; }
    public void clearAccountForm() { txtNom.setText(""); txtPrenom.setText(""); txtEmail.setText(""); txtLogin.setText(""); txtPass.setText(""); }
}