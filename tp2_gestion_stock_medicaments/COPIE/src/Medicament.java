import java.time.LocalDate;
import java.util.*;

public class Medicament {
    private String nom;
    private List<Lot> lots;

    public Medicament(String nom) {
        this.nom = nom;
        this.lots = new ArrayList<>();
    }

    public void ajouterLot(int quantite, LocalDate dateExpiration) {
        lots.add(new Lot(quantite, dateExpiration));
        lots.sort(Comparator.comparing(Lot::getDateExpiration));
    }

    public void supprimerMedicamentsExpires(LocalDate dateCourante) {
        lots.removeIf(lot -> lot.getDateExpiration().isBefore(dateCourante));
    }

    public int getQuantiteTotale(LocalDate dateCourante) {
        return lots.stream().filter(lot -> lot.getDateExpiration().isAfter(dateCourante))
                .mapToInt(Lot::getQuantite).sum();
    }

    public void retirerQuantite(int quantite, LocalDate dateCourante) {
        int reste = quantite;
        Iterator<Lot> it = lots.iterator();
        while (it.hasNext() && reste > 0) {
            Lot lot = it.next();
            if (lot.getDateExpiration().isAfter(dateCourante)) {
                if (lot.getQuantite() <= reste) {
                    reste -= lot.getQuantite();
                    it.remove();
                } else {
                    lot.setQuantite(lot.getQuantite() - reste);
                    reste = 0;
                }
            }
        }
    }

    public List<Lot> getLots() {
        return lots;
    }
}
