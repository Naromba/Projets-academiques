// Auteurs: Naromba Condé - 20251772
//          Lallia Diakité - 20256054
// Date: 31 Mai 2024
// Description: Calcule la distance entre deux points géographiques en utilisant la formule de Haversine.
public class Haversine {
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Rayon de la terre en km
        double latDistance = Math.toRadians(lat2 - lat1);   // Conversion de la différence de latitude en radians
        double lonDistance = Math.toRadians(lon2 - lon1);   // Conversion de la différence de longitude en radians

        // Calcul intermédiaire de la formule de Haversine
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        // Calcul de l'angle central
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calcul de la distance en utilisant le rayon de la Terre
        double distance = R * c; // conversion en kilomètres
        return distance;
    }
}

