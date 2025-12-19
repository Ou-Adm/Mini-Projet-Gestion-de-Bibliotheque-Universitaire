package estm.biblio.model;

public class Livre {
    private String isbn;
    private String titre;
    private String auteur;
    private String categorie;
    private int stock;

    public Livre(String isbn, String titre, String auteur, String categorie, int stock) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.stock = stock;
    }

    public String getIsbn() { return isbn; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public String getCategorie() { return categorie; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return titre + " (" + isbn + ")";
    }
}