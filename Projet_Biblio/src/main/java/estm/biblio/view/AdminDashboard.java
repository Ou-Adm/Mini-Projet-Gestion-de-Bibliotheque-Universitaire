package estm.biblio.view;

import estm.biblio.model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboard extends JFrame {

    // Couleurs
    private final Color DARK_BG = new Color(45, 52, 54);
    private final Color PANEL_BG = new Color(60, 67, 70);
    private final Color TEXT_WHITE = new Color(240, 240, 240);
    private final Color BTN_BLUE = new Color(9, 132, 227);
    private final Color BTN_GREEN = new Color(0, 184, 148);
    private final Color BTN_ORANGE = new Color(225, 112, 85); // Pour modif
    private final Color BTN_RED = new Color(214, 48, 49);

    private JButton btnLogout = new JButton("Se Déconnecter");

    // ONGLET 1 : EMPRUNTS
    // J'ajoute une colonne ID (Index 0) et ISBN (Index 3)
    public DefaultTableModel modelEmprunts = new DefaultTableModel(new String[]{"ID", "Emprunteur", "Livre", "ISBN", "Date Prêt", "Retour Prévu", "Statut"}, 0);
    private JTable tableEmprunts = new JTable(modelEmprunts);
    private JButton btnRetourner = new JButton("Enregistrer Retour"); // NOUVEAU

    // ONGLET 2 : LIVRES
    private JTextField txtSearch = new JTextField(20); // NOUVEAU
    private JButton btnSearch = new JButton("Rechercher"); // NOUVEAU

    private JTextField txtIsbn = new JTextField(15);
    private JTextField txtTitre = new JTextField(15);
    private JTextField txtAuteur = new JTextField(15);
    private JComboBox<String> cbCategorie = new JComboBox<>(new String[]{"Informatique", "Mathématiques", "Physique", "Droit", "Roman", "Autre"});
    private JTextField txtStock = new JTextField(5);

    private JButton btnAddLivre = new JButton("Ajouter");
    private JButton btnUpdLivre = new JButton("Modifier"); // NOUVEAU
    private JButton btnDelLivre = new JButton("Supprimer");

    public DefaultTableModel modelLivres = new DefaultTableModel(new String[]{"ISBN", "Titre", "Auteur", "Catégorie", "Stock"}, 0);
    public JTable tableLivres = new JTable(modelLivres); // Public pour ajouter le listener

    // ONGLET 3 : COMPTES
    private JTextField txtNom = new JTextField(15);
    private JTextField txtPrenom = new JTextField(15);
    private JTextField txtEmail = new JTextField(15);
    private JTextField txtLogin = new JTextField(15);
    private JPasswordField txtPass = new JPasswordField(15);
    private JComboBox<String> cbRole = new JComboBox<>(new String[]{"USER (Adhérent)", "ADMIN"});

    private JButton btnCreateAccount = new JButton("Créer");
    private JButton btnUpdAccount = new JButton("Modifier Info"); // NOUVEAU
    private JButton btnDelAccount = new JButton("Supprimer");

    public DefaultTableModel modelComptes = new DefaultTableModel(new String[]{"ID", "Nom & Prénom", "Email", "Login", "Rôle"}, 0);
    public JTable tableComptes = new JTable(modelComptes);

    public AdminDashboard() {
        setTitle("Administration - Dashboard Complet");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(DARK_BG);

        // HEADER
        JPanel pHeader = new JPanel(new BorderLayout());
        pHeader.setBackground(new Color(30, 30, 30));
        pHeader.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel lblTitle = new JLabel("DASHBOARD ADMIN");
        lblTitle.setForeground(TEXT_WHITE); lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        styleButton(btnLogout, BTN_RED);
        pHeader.add(lblTitle, BorderLayout.WEST); pHeader.add(btnLogout, BorderLayout.EAST);
        add(pHeader, BorderLayout.NORTH);

        // TABS
        JTabbedPane tabs = new JTabbedPane();

        // --- TAB 1 : PRÊTS ---
        JPanel p1 = new JPanel(new BorderLayout());
        p1.setBackground(DARK_BG);
        p1.add(new JScrollPane(tableEmprunts), BorderLayout.CENTER);
        JPanel p1Bot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p1Bot.setBackground(DARK_BG);
        styleButton(btnRetourner, BTN_GREEN); // Bouton retour
        p1Bot.add(btnRetourner);
        p1.add(p1Bot, BorderLayout.SOUTH);

        // --- TAB 2 : LIVRES ---
        JPanel p2 = new JPanel(new BorderLayout());
        p2.setBackground(DARK_BG);

        // Barre de recherche (Haut)
        JPanel p2Top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2Top.setBackground(DARK_BG);
        JLabel lblSearch = new JLabel("Rechercher :"); lblSearch.setForeground(TEXT_WHITE);
        p2Top.add(lblSearch); p2Top.add(txtSearch);
        styleButton(btnSearch, BTN_BLUE); p2Top.add(btnSearch);
        p2.add(p2Top, BorderLayout.NORTH);

        // Formulaire (Gauche)
        JPanel p2Form = new JPanel(new GridBagLayout());
        p2Form.setBackground(PANEL_BG);
        p2Form.setBorder(createTitleBorder("Gestion Livre"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5); gbc.anchor=GridBagConstraints.WEST;

        addFormRow(p2Form, gbc, 0, "ISBN:", txtIsbn);
        addFormRow(p2Form, gbc, 1, "Titre:", txtTitre);
        addFormRow(p2Form, gbc, 2, "Auteur:", txtAuteur);
        addFormRow(p2Form, gbc, 3, "Catégorie:", cbCategorie);
        addFormRow(p2Form, gbc, 4, "Stock:", txtStock);

        JPanel p2Btns = new JPanel(new GridLayout(1, 3, 5, 0));
        p2Btns.setBackground(PANEL_BG);
        styleButton(btnAddLivre, BTN_GREEN); p2Btns.add(btnAddLivre);
        styleButton(btnUpdLivre, BTN_ORANGE); p2Btns.add(btnUpdLivre);
        styleButton(btnDelLivre, BTN_RED); p2Btns.add(btnDelLivre);

        gbc.gridx=0; gbc.gridy=5; gbc.gridwidth=2; gbc.fill=GridBagConstraints.HORIZONTAL;
        p2Form.add(p2Btns, gbc);

        p2.add(p2Form, BorderLayout.WEST);
        p2.add(new JScrollPane(tableLivres), BorderLayout.CENTER);

        // --- TAB 3 : UTILISATEURS ---
        JPanel p3 = new JPanel(new BorderLayout());
        p3.setBackground(DARK_BG);

        JPanel p3Form = new JPanel(new GridBagLayout());
        p3Form.setBackground(PANEL_BG);
        p3Form.setBorder(createTitleBorder("Gestion Comptes"));

        gbc.gridwidth=1; gbc.fill=GridBagConstraints.NONE;
        addFormRowH(p3Form, gbc, 0, "Nom:", txtNom);
        addFormRowH(p3Form, gbc, 2, "Prénom:", txtPrenom);
        addFormRowH(p3Form, gbc, 4, "Email:", txtEmail);
        gbc.gridy=1;
        addFormRowH(p3Form, gbc, 0, "Login:", txtLogin);
        addFormRowH(p3Form, gbc, 2, "Pass:", txtPass);
        addFormRowH(p3Form, gbc, 4, "Rôle:", cbRole);

        JPanel p3Btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p3Btns.setBackground(DARK_BG);
        styleButton(btnCreateAccount, BTN_BLUE); p3Btns.add(btnCreateAccount);
        styleButton(btnUpdAccount, BTN_ORANGE); p3Btns.add(btnUpdAccount);
        styleButton(btnDelAccount, BTN_RED); p3Btns.add(btnDelAccount);

        p3.add(p3Form, BorderLayout.NORTH);
        p3.add(new JScrollPane(tableComptes), BorderLayout.CENTER);
        p3.add(p3Btns, BorderLayout.SOUTH);

        tabs.addTab("Prêts", p1); tabs.addTab("Livres", p2); tabs.addTab("Utilisateurs", p3);
        add(tabs, BorderLayout.CENTER);
    }

    // --- Helpers UI ---
    private void styleButton(JButton btn, Color c) {
        btn.setBackground(c); btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false); btn.setBorderPainted(false); btn.setOpaque(true);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    private TitledBorder createTitleBorder(String t) { TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), t); b.setTitleColor(TEXT_WHITE); return b; }
    private void addFormRow(JPanel p, GridBagConstraints gbc, int y, String l, JComponent f) {
        gbc.gridx=0; gbc.gridy=y; JLabel lbl=new JLabel(l); lbl.setForeground(TEXT_WHITE); p.add(lbl, gbc);
        gbc.gridx=1; f.setPreferredSize(new Dimension(140, 25)); p.add(f, gbc);
    }
    private void addFormRowH(JPanel p, GridBagConstraints gbc, int x, String l, JComponent f) {
        gbc.gridx=x; JLabel lbl=new JLabel(l); lbl.setForeground(TEXT_WHITE); p.add(lbl, gbc);
        gbc.gridx=x+1; f.setPreferredSize(new Dimension(110, 25)); p.add(f, gbc);
    }

    // --- GETTERS & LISTENERS ---

    // Prêts
    public void addRetourListener(ActionListener l) { btnRetourner.addActionListener(l); }
    public int getSelectedEmpruntId() { int r=tableEmprunts.getSelectedRow(); return r!=-1 ? (int)tableEmprunts.getValueAt(r,0) : -1; }
    public String getSelectedEmpruntIsbn() { int r=tableEmprunts.getSelectedRow(); return r!=-1 ? (String)tableEmprunts.getValueAt(r,3) : null; }

    // Livres
    public void addSearchListener(ActionListener l) { btnSearch.addActionListener(l); }
    public String getSearchText() { return txtSearch.getText(); }
    public void addAddLivreListener(ActionListener l) { btnAddLivre.addActionListener(l); }
    public void addUpdLivreListener(ActionListener l) { btnUpdLivre.addActionListener(l); }
    public void addDelLivreListener(ActionListener l) { btnDelLivre.addActionListener(l); }
    public void addTableLivreMouseListener(MouseAdapter l) { tableLivres.addMouseListener(l); } // Pour remplir le formulaire au clic

    public Livre getLivreForm() {
        try { return new Livre(txtIsbn.getText(), txtTitre.getText(), txtAuteur.getText(), (String)cbCategorie.getSelectedItem(), Integer.parseInt(txtStock.getText())); }
        catch(Exception e){ return null; }
    }
    public void setLivreForm(String i, String t, String a, String c, int s) { txtIsbn.setText(i); txtTitre.setText(t); txtAuteur.setText(a); cbCategorie.setSelectedItem(c); txtStock.setText(String.valueOf(s)); }
    public void clearLivreForm() { txtIsbn.setText(""); txtTitre.setText(""); txtAuteur.setText(""); txtStock.setText(""); }
    public String getSelectedIsbn() { int r=tableLivres.getSelectedRow(); return r!=-1 ? (String)tableLivres.getValueAt(r,0) : null; }

    // Comptes
    public void addCreateAccountListener(ActionListener l) { btnCreateAccount.addActionListener(l); }
    public void addUpdAccountListener(ActionListener l) { btnUpdAccount.addActionListener(l); }
    public void addDelAccountListener(ActionListener l) { btnDelAccount.addActionListener(l); }
    public void addTableAccountMouseListener(MouseAdapter l) { tableComptes.addMouseListener(l); }

    public Adherent getAdherentData() { return new Adherent(0, txtNom.getText(), txtPrenom.getText(), txtEmail.getText(), "ACTIF"); }
    public String getLogin() { return txtLogin.getText(); }
    public String getPass() { return new String(txtPass.getPassword()); }
    public String getRole() { return ((String)cbRole.getSelectedItem()).startsWith("ADMIN") ? "ADMIN" : "USER"; }
    public void setAccountForm(String n, String p, String e, String l, String r) { txtNom.setText(n); txtPrenom.setText(p); txtEmail.setText(e); txtLogin.setText(l); cbRole.setSelectedItem(r.equals("ADMIN")?"ADMIN":"USER (Adhérent)"); }
    public void clearAccountForm() { txtNom.setText(""); txtPrenom.setText(""); txtEmail.setText(""); txtLogin.setText(""); txtPass.setText(""); }
    public int getSelectedAccountId() { int r=tableComptes.getSelectedRow(); return r!=-1 ? (int)tableComptes.getValueAt(r,0) : -1; }

    // Logout
    public void addLogoutListener(ActionListener l) { btnLogout.addActionListener(l); }
}