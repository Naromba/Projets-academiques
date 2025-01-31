// Auteurs: Naromba Condé - 20251772
//          Lallia Diakité - 20256054
// Date: 31 Mai 2024
// Description: Cette classe représente un camion avec des informations sur sa position géographique (latitude et longitude),
// sa capacité de chargement et la cargaison actuelle.
public class Truck {
    private double latitude;
    private double longitude;
    private int capacity;
    private int cargaison;

    // Constructeur pour initialiser les attributs du camion
    public Truck(double latitude, double longitude, int capacity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
        this.cargaison = 0;         // Initialiser la cargaison à 0
    }

    // Getter pour la latitude
    public double getLatitude() {
        return latitude;
    }

    // Setter pour la latitude
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // Getter pour la longitude
    public double getLongitude() {
        return longitude;
    }

    // Setter pour la longitude
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Getter pour la capacité de chargement
    public int getCapacity() {
        return capacity;
    }

    // Setter pour la capacité de chargement
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Getter pour la cargaison actuelle
    public int getCargaison() {
        return cargaison;
    }

    // Setter pour la cargaison actuelle
    public void setCargaison(int cargaison) {
        this.cargaison = cargaison;
    }


}
