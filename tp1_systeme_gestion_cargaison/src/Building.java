import java.util.Locale;

// Auteurs: Naromba Condé - 20251772
//          Lallia Diakité - 20256054
// Date: 31 Mai 2024
// Description: Cette classe représente un bâtiment d'entreposage avec des informations sur le nombre de boîtes,
// la position géographique (latitude et longitude) et la distance par rapport à un camion.
public class Building {
    private int boxes;
    private double latitude;
    private double longitude;
    private double distance;

    // Constructeur pour initialiser les attributs du bâtiment
    public Building(int boxes, double latitude, double longitude) {
        this.boxes = boxes;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getter pour le nombre de boîtes
    public int getBoxes() {
        return boxes;
    }

    // Setter pour le nombre de boîtes
    public void setBoxes(int boxes) {
        this.boxes = boxes;
    }

    // Getter pour la latitude
    public double getLatitude() {
        return latitude;
    }

    // Getter pour la longitude
    public double getLongitude() {
        return longitude;
    }

    // Getter pour la distance
    public double getDistance() {
        return distance;
    }

    // Setter pour la distance
    public void setDistance(double distance) {
        this.distance = distance;
    }

    // Méthode pour calculer la distance entre le bâtiment et un camion donné par sa latitude et longitude
    public void calculerDistance(double latitudeTruck, double longitudeTruck) {
        this.distance = Haversine.distance(latitudeTruck, longitudeTruck, this.latitude, this.longitude);
    }

    // Méthode pour obtenir la distance formatée en mètres, arrondie à une décimale
    public String getFormattedDistance() {
        // Convertir la distance en kilomètres en mètres
        double adjustedDistance = this.distance * 1000;
        // Arrondir la distance à une décimale
        double roundedDistance = Math.round(adjustedDistance * 10.0) / 10.0;
        // Retourner la distance formatée en chaîne de caractères
        return String.format(Locale.US, "%.1f", roundedDistance);

    }

    // Méthode pour obtenir une représentation en chaîne de caractères du bâtiment
    @Override
    public String toString() {
        return "Distance:" + getFormattedDistance() + "\t\tNumber of boxes:" + boxes + "\t\tPosition:(" + latitude + "," + longitude + ")";
    }
}

