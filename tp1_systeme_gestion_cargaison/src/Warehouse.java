// Auteurs: Naromba Condé - 20251772
//          Lallia Diakité - 20256054
// Date: 31 Mai 2024
// Description: Cette classe représente un entrepôt qui contient des bâtiments et un camion,
// et permet de lire les données d'entrée, initialiser le camion, trier les bâtiments par distance,
// et écrire les données de sortie.

import java.io.*;
import java.util.*;

public class Warehouse {
    private List<Building> buildings;
    private Truck truck;
    private int totalBoxes;
    private int remainingBoxes;

    // Constructeur pour initialiser l'entrepôt avec un camion et sa capacité
    public Warehouse(Truck truck, int capacity) {
        this.buildings = new ArrayList<>();
        this.truck = truck;
        this.truck.setCapacity(capacity);
    }

    // Méthode pour lire les données d'entrée depuis un fichier
    public void readInput(String inputFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String ligne;
        buildings.clear();

        // Lecture de la première ligne pour obtenir le nombre total de boîtes et la capacité du camion
        if ((ligne = br.readLine()) != null) {
            String[] premiereLigne = ligne.split(" ");
            this.totalBoxes = Integer.parseInt(premiereLigne[0]);
            this.remainingBoxes = totalBoxes;
            int capaciteCamion = Integer.parseInt(premiereLigne[1]);
            truck.setCapacity(capaciteCamion);
        }

        // Lecture des lignes suivantes pour obtenir les informations des bâtiments
        while ((ligne = br.readLine()) != null) {
            if (ligne.trim().isEmpty()) continue;

            String[] parties = ligne.split(" ");
            for (int i = 0; i < parties.length; i++) {
                if (parties[i].contains("(")) {
                    // La partie précédente est le nombre de boîtes
                    int boxes = Integer.parseInt(parties[i - 1]);
                    // Extraction des coordonnées
                    String[] coords = parties[i].replace("(", "").replace(")", "").split(",");
                    double latitude = Double.parseDouble(coords[0]);
                    double longitude = Double.parseDouble(coords[1]);
                    // Ajout du bâtiment à la liste
                    buildings.add(new Building(boxes, latitude, longitude));
                }
            }
        }

        br.close();
    }

    // Méthode pour initialiser le camion à la position du bâtiment avec le maximum de boîtes
    public void initializeTruck() {
        // Trouver le bâtiment avec le plus grand nombre de boîtes
        Building maxBoxesBuilding = buildings.stream().max(Comparator.comparingInt(Building::getBoxes)).orElse(null);
        if (maxBoxesBuilding != null) {
            // Positionner le camion à la position de ce bâtiment
            truck.setLatitude(maxBoxesBuilding.getLatitude());
            truck.setLongitude(maxBoxesBuilding.getLongitude());
            // Charger le camion avec autant de boîtes que possible
            int boxesToLoad = Math.min(maxBoxesBuilding.getBoxes(), truck.getCapacity());
            truck.setCargaison(boxesToLoad);
            maxBoxesBuilding.setBoxes(maxBoxesBuilding.getBoxes() - boxesToLoad);
            remainingBoxes -= boxesToLoad;
        }
    }

    // Méthode pour trier les bâtiments par distance par rapport au camion (Tri Java)
    public void sortBuildingsByDistance() {
        for (Building building : buildings) {
            building.calculerDistance(truck.getLatitude(), truck.getLongitude());
        }
        buildings.sort(Comparator.comparingDouble(Building::getDistance)
                .thenComparingDouble(Building::getLatitude)
                .thenComparingDouble(Building::getLongitude));
    }

    // Méthode de tri par insertion pour trier les bâtiments par distance
    public void sortBuildingsByDistanceInsertion() {
        for (Building building : buildings) {
            building.calculerDistance(truck.getLatitude(), truck.getLongitude());
        }
        for (int i = 1; i < buildings.size(); i++) {
            Building key = buildings.get(i);
            int j = i - 1;
            // Déplacement des éléments ayant une distance plus grande que la clé
            while (j >= 0 && buildings.get(j).getDistance() > key.getDistance()) {
                buildings.set(j + 1, buildings.get(j));
                j = j - 1;
            }
            buildings.set(j + 1, key);
        }
    }

    // Méthode de tri rapide pour trier les bâtiments par distance
    public void sortBuildingsByDistanceQuickSort() {
        for (Building building : buildings) {
            building.calculerDistance(truck.getLatitude(), truck.getLongitude());
        }
        quickSort(0, buildings.size() - 1);
    }

    // Méthode de tri rapide pour trier les bâtiments par distance (helper)
    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);

            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    // Méthode de partition pour le tri rapide
    private int partition(int low, int high) {
        Building pivot = buildings.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (buildings.get(j).getDistance() <= pivot.getDistance()) {
                i++;
                // Échanger les éléments buildings[i] et buildings[j]
                Building temp = buildings.get(i);
                buildings.set(i, buildings.get(j));
                buildings.set(j, temp);
            }
        }

        // Échanger les éléments buildings[i+1] et buildings[high] (ou pivot)
        Building temp = buildings.get(i + 1);
        buildings.set(i + 1, buildings.get(high));
        buildings.set(high, temp);
        return i + 1;
    }

    // Méthode pour écrire les données de sortie dans un fichier
    public void writeOutput(String outputFile) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        bw.write("Truck position: (" + truck.getLatitude() + "," + truck.getLongitude() + ")");
        bw.newLine();

        int totalLoadedBoxes = truck.getCargaison(); // Boîtes déjà chargées initialement
        for (Building building : buildings) {
            // Arrêter si le camion est plein ou si le nombre total de boîtes à charger est atteint
            if (totalLoadedBoxes >= truck.getCapacity() || totalLoadedBoxes >= totalBoxes) break; // Arrêter si le camion est plein ou si le nombre total de boîtes à charger est atteint

            int boxesToLoad = Math.min(building.getBoxes(), Math.min(truck.getCapacity() - totalLoadedBoxes, totalBoxes - totalLoadedBoxes));
            totalLoadedBoxes += boxesToLoad;
            building.setBoxes(building.getBoxes() - boxesToLoad);

            bw.write(building.toString());
            bw.newLine();
        }

        bw.close();
    }

}
