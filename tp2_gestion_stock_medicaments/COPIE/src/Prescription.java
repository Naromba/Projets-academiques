import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Prescription {
    private String nomMedicament;
    private int doseTraitement;
    private int repetition;

    public Prescription(String nomMedicament, int doseTraitement, int repetition) {
        this.nomMedicament = nomMedicament;
        this.doseTraitement = doseTraitement;
        this.repetition = repetition;
    }

    public String traiter(Map<String, Medicament> stock, List<Commande> commandes, LocalDate dateCourante) {
        if (!stock.containsKey(nomMedicament)) {
            commandes.add(new Commande(nomMedicament, doseTraitement * repetition));
            return nomMedicament + " " + doseTraitement + " " + repetition + " COMMANDE";
        }

        Medicament medicament = stock.get(nomMedicament);
        int quantiteTotaleDisponible = medicament.getQuantiteTotale(dateCourante);
        int quantiteNecessaire = doseTraitement * repetition;

        if (quantiteTotaleDisponible < quantiteNecessaire) {
            commandes.add(new Commande(nomMedicament, quantiteNecessaire - quantiteTotaleDisponible));
            return nomMedicament + " " + doseTraitement + " " + repetition + " COMMANDE";
        } else {
            medicament.retirerQuantite(quantiteNecessaire, dateCourante);
            return nomMedicament + " " + doseTraitement + " " + repetition + " OK";
        }
    }
}
