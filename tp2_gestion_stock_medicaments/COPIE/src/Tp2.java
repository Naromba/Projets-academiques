import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Tp2 {
    private Map<String, Medicament> stock;
    private List<Commande> commandes;
    private LocalDate dateCourante;
    private BufferedWriter writer;
    private int prescriptionCounter = 1;

    public Tp2() {
        this.stock = new HashMap<>();
        this.commandes = new ArrayList<>();
    }

    public void lireFichier(String nomFichierEntree, String nomFichierSortie) throws IOException {
        writer = new BufferedWriter(new FileWriter(nomFichierSortie));
        BufferedReader br = new BufferedReader(new FileReader(nomFichierEntree));
        StringBuilder transaction = new StringBuilder();
        String ligne;
        while ((ligne = br.readLine()) != null) {
            if (ligne.trim().endsWith(";")) {
                transaction.append(ligne.trim());
                try {
                    traiterTransaction(transaction.toString());
                    writer.newLine();  // Ajout d'une ligne vide après chaque transaction
                } catch (Exception e) {
                    writer.write("Erreur lors du traitement de la transaction : " + transaction.toString());
                    writer.newLine();
                    writer.write("Exception: " + e.getMessage());
                    writer.newLine();
                }
                transaction.setLength(0); // Reset the transaction
            } else {
                transaction.append(ligne.trim()).append(" ");
            }
        }
        br.close();
        writer.close();
    }

    private void traiterTransaction(String transaction) throws IOException {
        String[] parties = transaction.split(" ", 2);
        if (parties.length < 2) {
            throw new IllegalArgumentException("Format de transaction invalide: " + transaction);
        }

        String typeTransaction = parties[0];
        switch (typeTransaction) {
            case "DATE":
                traiterDate(parties[1].trim().replace(";", "").trim());
                break;
            case "PRESCRIPTION":
                traiterPrescription(parties[1].replace(":", "").trim().replace(";", "").split("\\s+"));
                break;
            case "APPROV":
                traiterApprovisionnement(parties[1].replace(":", "").trim().replace(";", "").split("\\s+"));
                break;
            case "STOCK":
                afficherStock();
                break;
            default:
                throw new IllegalArgumentException("Type de transaction inconnu: " + typeTransaction);
        }
    }

    private void traiterDate(String dateStr) throws IOException {
        try {
            this.dateCourante = LocalDate.parse(dateStr);
            supprimerMedicamentsExpires();
            writer.write(dateCourante.toString() + " OK");
            writer.newLine();
            if (!commandes.isEmpty()) {
                writer.write(dateCourante.toString() + " COMMANDES :");
                writer.newLine();
                Map<String, Integer> commandesMap = new HashMap<>();
                for (Commande commande : commandes) {
                    commandesMap.merge(commande.getNomMedicament(), commande.getQuantite(), Integer::sum);
                }
                List<Map.Entry<String, Integer>> sortedCommandes = new ArrayList<>(commandesMap.entrySet());
                sortedCommandes.sort(Map.Entry.comparingByKey());
                for (Map.Entry<String, Integer> entry : sortedCommandes) {
                    writer.write(entry.getKey() + " " + entry.getValue());
                    writer.newLine();
                }
                commandes.clear();
            }
        } catch (DateTimeParseException e) {
            writer.write("Erreur de format de la date : " + dateStr);
            writer.newLine();
        }
    }

    private void supprimerMedicamentsExpires() {
        for (Medicament medicament : stock.values()) {
            medicament.supprimerMedicamentsExpires(dateCourante);
        }
    }

    private void traiterPrescription(String[] details) throws IOException {
        try {
            writer.write("PRESCRIPTION " + prescriptionCounter++);
            writer.newLine();
            for (int i = 0; i < details.length; i += 3) {
                if (i + 2 >= details.length) {
                    throw new IllegalArgumentException("Détails de prescription incomplets: " + Arrays.toString(details));
                }
                String nomMedicament = details[i];
                int doseTraitement = Integer.parseInt(details[i + 1].trim());
                int repetition = Integer.parseInt(details[i + 2].trim());
                Prescription prescription = new Prescription(nomMedicament, doseTraitement, repetition);
                String resultat = prescription.traiter(stock, commandes, dateCourante);
                writer.write(resultat);
                writer.newLine();
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            writer.write("Erreur de format dans les détails de la prescription : " + Arrays.toString(details));
            writer.newLine();
        } catch (Exception e) {
            writer.write("Erreur lors du traitement de la prescription : " + Arrays.toString(details));
            writer.newLine();
            writer.write("Exception: " + e.getMessage());
            writer.newLine();
        }
    }

    private void traiterApprovisionnement(String[] details) throws IOException {
        try {
            for (int i = 0; i < details.length; i += 3) {
                if (i + 2 >= details.length) {
                    throw new IllegalArgumentException("Détails d'approvisionnement incomplets: " + Arrays.toString(details));
                }
                String nomMedicament = details[i];
                int quantite = Integer.parseInt(details[i + 1].trim());
                LocalDate dateExpiration = LocalDate.parse(details[i + 2].trim());
                Medicament medicament = stock.getOrDefault(nomMedicament, new Medicament(nomMedicament));
                medicament.ajouterLot(quantite, dateExpiration);
                stock.put(nomMedicament, medicament);
            }
            writer.write("APPROV OK");
            writer.newLine();
        } catch (NumberFormatException | DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            writer.write("Erreur de format dans les détails de l'approvisionnement : " + Arrays.toString(details));
            writer.newLine();
        }
    }

    private void afficherStock() throws IOException {
        writer.write("STOCK " + dateCourante.toString());
        writer.newLine();
        List<Map.Entry<String, Medicament>> sortedStock = new ArrayList<>(stock.entrySet());
        sortedStock.sort(Map.Entry.comparingByKey()); // Trie les médicaments par ordre alphabétique
        for (Map.Entry<String, Medicament> entry : sortedStock) {
            Medicament medicament = entry.getValue();
            for (Lot lot : medicament.getLots()) {
                if (lot.getDateExpiration().isAfter(dateCourante)) {
                    writer.write(entry.getKey() + " " + lot.getQuantite() + " " + lot.getDateExpiration().toString());
                    writer.newLine();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java Tp2 <input file> <output file>");
            return;
        }
        Tp2 tp2 = new Tp2();
        tp2.lireFichier(args[0], args[1]);
    }
}
