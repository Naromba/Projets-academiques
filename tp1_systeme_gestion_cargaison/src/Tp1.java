// Auteurs: Naromba Condé - 20251772
//          Lallia Diakité - 20256054
// Date: 31 Mai 2024
// Description: Ce programme principal lit les données des bâtiments et du camion,
// les initialise, trie les bâtiments par distance avec différentes méthodes de tri,
// et écrit les résultats dans des fichiers de sortie.

import java.io.IOException;

public class Tp1 {
    public static void main(String[] args) {
        // Vérification du nombre d'arguments
        if (args.length != 2) {
            System.out.println("Erreur sur le nombre d'arguments");
            return;
        }

        String input = args[0];     // Fichier d'entrée
        String output = args[1];    // Préfixe du fichier de sortie

        try {
            // Initialisation du camion
            Truck truck = new Truck(0, 0, 100);
            // Création de l'entrepôt avec le camion
            Warehouse warehouse = new Warehouse(truck, 100);

            // Traitement du cas avec le tri Java
            // Lecture des données d'entrée et initialisation du camion
            warehouse.readInput(input);
            warehouse.initializeTruck();
            // Tri des bâtiments par distance en utilisant le tri par distance
            warehouse.sortBuildingsByDistance();
            // Écriture des résultats dans un fichier de sortie
            warehouse.writeOutput(output + "_java.txt");

            // Traitement du cas avec le tri par insertion
            warehouse.readInput(input);
            warehouse.initializeTruck();
            warehouse.sortBuildingsByDistanceInsertion();
            warehouse.writeOutput(output + "_insertion.txt");

            // Traitement du cas avec le tri rapide (quick sort)
            warehouse.readInput(input);
            warehouse.initializeTruck();
            warehouse.sortBuildingsByDistanceQuickSort();
            warehouse.writeOutput(output + "_quicksort.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
