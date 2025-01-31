import java.io.IOException;
import java.util.List;

public class Tp3 {
    public static void main(String[] args) {
        // Vérifier qu'on a bien 2 arguments
        if (args.length != 2) {
            System.out.println("Usage: java Tp3 <input file> <output file>");
            return;
        }

        // Créer un nouveau graphe
        Graphe graphe = new Graphe();

        // Lire le fichier d'entrée et remplir le graphe
        try {
            graphe.readInput(args[0]);
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        // Appliquer l'algorithme de Kruskal sur le graphe
        List<Street> arm = graphe.kruskal();

        // Écrire le fichier de sortie
        try {
            graphe.writeOutput(args[1], arm);
        } catch (IOException e) {
            System.out.println("Error writing output file: " + e.getMessage());
        }
    }
}
