import java.time.LocalDate;

public class Lot {
    private int quantite;
    private LocalDate dateExpiration;

    public Lot(int quantite, LocalDate dateExpiration) {
        this.quantite = quantite;
        this.dateExpiration = dateExpiration;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }
}
