import java.io.*;
import java.util.*;

public class Graphe {
    private Map<String, Site> sites;
    private Map<Site, List<Street>> adjacence;

    public Graphe() {
        this.sites = new HashMap<>();
        this.adjacence = new HashMap<>();
    }

    public void addStreet(Street street) {
        adjacence.putIfAbsent(street.getStart(), new ArrayList<>());
        adjacence.get(street.getStart()).add(street);
    }

    public List<Street> getStreets(Site site) {
        return adjacence.get(site);
    }

    public Set<Site> getSites() {
        return adjacence.keySet();
    }

    public void readInput(String inputFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        int id = 0; // Ajouter un compteur pour les identifiants des sites

        // Lire les sites
        while ((line = reader.readLine()) != null && !line.equals("---")) {
            Site site = new Site(line.trim(), id++);
            sites.put(line.trim(), site);
            adjacence.put(site, new ArrayList<>());
        }

        // Lire les rues
        while ((line = reader.readLine()) != null && !line.equals("---")) {
            String[] parts = line.split("[: ;]+");
            if (parts.length != 4) {
                System.out.println("Erreur de format dans la ligne : " + line);
                continue; // Passer à la ligne suivante
            }
            String streetName = parts[0].trim();
            Site start = sites.get(parts[1].trim());
            Site end = sites.get(parts[2].trim());
            int cost;
            try {
                cost = Integer.parseInt(parts[3].trim());
            } catch (NumberFormatException e) {
                System.out.println("Erreur de format pour le coût dans la ligne : " + line);
                continue; // Passer à la ligne suivante
            }
            if (start == null || end == null) {
                System.out.println("Site inconnu dans la ligne : " + line);
                continue; // Passer à la ligne suivante
            }
            Street street = new Street(streetName, start, end, cost);
            addStreet(street);
        }
        reader.close();
    }

    public List<Street> kruskal() {
        List<Street> edges = new ArrayList<>();
        for (List<Street> streets : adjacence.values()) {
            edges.addAll(streets);
        }
        edges.sort(Comparator.comparingInt(Street::getCost)
                .thenComparing(s -> s.getStart().getName())
                .thenComparing(s -> s.getEnd().getName()));

        UnionFind uf = new UnionFind(sites.size());
        List<Street> mst = new ArrayList<>();

        for (Street street : edges) {
            int root1 = uf.find(street.getStart().getId());
            int root2 = uf.find(street.getEnd().getId());

            if (root1 != root2) {
                mst.add(street);
                uf.union(root1, root2);
            }
        }

        return mst;
    }



/*
    public void writeOutput(String outputFile, List<Street> mst) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        // Ecrire les sites
        List<Site> sortedSites = new ArrayList<>(sites.values());
        sortedSites.sort(Comparator.comparing(Site::getName));
        for (Site site : sortedSites) {
            writer.write(site.getName());
            writer.newLine();
        }

        // Trier les rues dans l'ordre alphanumérique
        mst.sort(Comparator.comparing((Street s) -> s.getStart().getName())
                .thenComparing(s -> s.getEnd().getName()));

        // Ecrire les rues
        for (Street street : mst) {
            writer.write(street.getName() + "\t" + street.getStart().getName() + "\t" + street.getEnd().getName() + "\t" + street.getCost());
            writer.newLine();
        }

        // Séparation
        writer.write("---");
        writer.newLine();

        // Ecrire le coût total
        int totalCost = mst.stream().mapToInt(Street::getCost).sum();
        writer.write(Integer.toString(totalCost));
        writer.newLine();

        writer.close();
    }*/



    public void writeOutput(String outputFile, List<Street> mst) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        // Ecrire les sites
        List<Site> sortedSites = new ArrayList<>(sites.values());
        sortedSites.sort(Comparator.comparing(Site::getName));
        for (Site site : sortedSites) {
            writer.write(site.getName());
            writer.newLine();
        }

        // Trier les rues dans l'ordre alphanumérique
        mst.sort(Comparator.comparing((Street s) -> s.getStart().getName())
                .thenComparing(s -> s.getEnd().getName()));

        // Ecrire les rues
        for (Street street : mst) {
            writer.write(street.getName() + "\t" + street.getStart().getName() + "\t" + street.getEnd().getName() + "\t" + street.getCost());
            writer.newLine();
        }

        // Séparation
        writer.write("---");
        writer.newLine();

        // Ecrire le coût total
        int totalCost = mst.stream().mapToInt(Street::getCost).sum();
        writer.write(Integer.toString(totalCost));
        writer.newLine();

        writer.close();
    }



}
