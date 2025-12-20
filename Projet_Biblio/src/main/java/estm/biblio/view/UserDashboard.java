package estm.biblio.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserDashboard extends JFrame {

    // Couleurs
    private final Color DARK_BG = new Color(45, 52, 54);
    private final Color HEADER_BG = new Color(30, 30, 30);
    private final Color TEXT_WHITE = new Color(245, 245, 245);
    private final Color BTN_RED = new Color(214, 48, 49);
    private final Color BTN_GREEN = new Color(0, 184, 148);
    private final Color BTN_BLUE = new Color(9, 132, 227); // Pour la recherche

    private JButton btnLogout = new JButton("Se Déconnecter");

    // NOUVEAU : Barre de recherche
    private JTextField txtSearch = new JTextField(20);
    private JButton btnSearch = new JButton("Rechercher");

    private JButton btnEmprunter = new JButton("Emprunter ce Livre");

    public DefaultTableModel modelLivres = new DefaultTableModel(new String[]{"ISBN", "Titre", "Auteur", "Catégorie", "Disponibilité"}, 0);
    private JTable tableLivres = new JTable(modelLivres);

    public DefaultTableModel modelMesEmprunts = new DefaultTableModel(new String[]{"Livre", "Date Prêt", "Date Limite"}, 0);
    private JTable tableEmprunts = new JTable(modelMesEmprunts);

    public UserDashboard(String userNom) {
        setTitle("Espace Étudiant - Dark Mode");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(DARK_BG);

        // --- HEADER ---
        JPanel pHeader = new JPanel(new BorderLayout());
        pHeader.setBackground(HEADER_BG);
        pHeader.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel("Bienvenue, " + userNom);
        lblTitle.setForeground(TEXT_WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));

        styleButton(btnLogout, BTN_RED);

        pHeader.add(lblTitle, BorderLayout.WEST);
        pHeader.add(btnLogout, BorderLayout.EAST);
        add(pHeader, BorderLayout.NORTH);

        // --- TABS ---
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // TAB 1 : CATALOGUE
        JPanel pCat = new JPanel(new BorderLayout());
        pCat.setBackground(DARK_BG);
        pCat.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panneau NORD (Titre + Recherche)
        JPanel pTop = new JPanel(new BorderLayout());
        pTop.setBackground(DARK_BG);
        pTop.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel l1 = new JLabel("Catalogue des Livres");
        l1.setForeground(TEXT_WHITE);
        l1.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Sous-panneau recherche
        JPanel pSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pSearch.setBackground(DARK_BG);
        JLabel lblS = new JLabel("Recherche :"); lblS.setForeground(TEXT_WHITE);
        pSearch.add(lblS);
        pSearch.add(txtSearch);
        styleButton(btnSearch, BTN_BLUE); // Style Bleu
        pSearch.add(btnSearch);

        pTop.add(l1, BorderLayout.WEST);
        pTop.add(pSearch, BorderLayout.EAST);

        pCat.add(pTop, BorderLayout.NORTH);
        pCat.add(new JScrollPane(tableLivres), BorderLayout.CENTER);

        JPanel pBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pBtn.setBackground(DARK_BG);
        styleButton(btnEmprunter, BTN_GREEN);
        pBtn.add(btnEmprunter);
        pCat.add(pBtn, BorderLayout.SOUTH);

        // TAB 2 : MES EMPRUNTS
        JPanel pEmp = new JPanel(new BorderLayout());
        pEmp.setBackground(DARK_BG);
        pEmp.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel l2 = new JLabel("Mes Emprunts en cours");
        l2.setForeground(TEXT_WHITE); l2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pEmp.add(l2, BorderLayout.NORTH);
        pEmp.add(new JScrollPane(tableEmprunts), BorderLayout.CENTER);

        tabs.addTab("Catalogue", pCat);
        tabs.addTab("Mes Emprunts", pEmp);
        add(tabs, BorderLayout.CENTER);
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); // Un peu plus petit
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Listeners
    public void addLogoutListener(ActionListener l) { btnLogout.addActionListener(l); }
    public void addEmprunterListener(ActionListener l) { btnEmprunter.addActionListener(l); }
    public void addSearchListener(ActionListener l) { btnSearch.addActionListener(l); } // NOUVEAU

    public String getSelectedIsbn() { int row = tableLivres.getSelectedRow(); return (row != -1) ? (String) tableLivres.getValueAt(row, 0) : null; }
    public String getSearchText() { return txtSearch.getText(); } // NOUVEAU
}