package estm.biblio.util;

import estm.biblio.model.Livre;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvImporter {
    public static List<Livre> importer(File file) {
        List<Livre> livres = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Format attendu: ISBN;Titre;Auteur;Categorie;Stock
                String[] data = line.split(";");
                if (data.length >= 5) {
                    livres.add(new Livre(
                            data[0],
                            data[1],
                            data[2],
                            data[3],
                            Integer.parseInt(data[4])
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livres;
    }
}