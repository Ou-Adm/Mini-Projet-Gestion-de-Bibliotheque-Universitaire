package estm.biblio.service;

import estm.biblio.dao.*;
import estm.biblio.model.Livre;
import estm.biblio.util.CsvImporter;
import estm.biblio.util.LoggerUtil;
import java.io.File;
import java.util.List;

public class BibliothequeService {
    private LivreDao livreDao = new LivreDao();
    private EmpruntDao empruntDao = new EmpruntDao();

    public String emprunterLivre(String isbn, int adherentId) {
        if (empruntDao.aDesRetardsGraves(adherentId)) {
            LoggerUtil.log("ECHEC EMPRUNT : Adh " + adherentId + " bloqué (retards > 10 jours).");
            return "BLOQUÉ : Vous avez des retards supérieurs à 10 jours.";
        }
        if (empruntDao.countActive(adherentId) >= 3) {
            return "Impossible : Limite de 3 livres atteinte.";
        }
        if (livreDao.getStock(isbn) <= 0) {
            return "Impossible : Stock épuisé.";
        }

        empruntDao.create(isbn, adherentId);
        livreDao.updateStock(isbn, -1);
        LoggerUtil.log("SUCCES EMPRUNT : Adherent " + adherentId + " - Livre " + isbn);
        return "Succès : Emprunt validé !";
    }

    public String importerCSV(File fichier) {
        List<Livre> livres = CsvImporter.importer(fichier);
        int ajout = 0;
        for (Livre l : livres) {
            if (!livreDao.exists(l.getIsbn())) {
                livreDao.save(l);
                ajout++;
            }
        }
        LoggerUtil.log("IMPORT CSV : " + ajout + " livres ajoutés.");
        return ajout + " livres importés.";
    }
}