package estm.biblio.model;

public class Adherent {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String statut;

    public Adherent(int id, String nom, String prenom, String email, String statut) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.statut = statut;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getStatut() { return statut; }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}