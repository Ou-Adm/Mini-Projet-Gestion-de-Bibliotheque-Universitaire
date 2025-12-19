package estm.biblio.model;

public class Utilisateur {
    private String login;
    private String role;
    private int adherentId;

    public Utilisateur(String login, String role, int adherentId) {
        this.login = login;
        this.role = role;
        this.adherentId = adherentId;
    }

    public String getLogin() { return login; }
    public String getRole() { return role; }
    public int getAdherentId() { return adherentId; }
}