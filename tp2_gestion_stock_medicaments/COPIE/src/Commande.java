public class Commande {
    private String nomMedicament;
    private int quantite;

    public Commande(String nomMedicament, int quantite) {
        this.nomMedicament = nomMedicament;
        this.quantite = quantite;
    }

    public String getNomMedicament() {
        return nomMedicament;
    }

    public int getQuantite() {
        return quantite;
    }
}
