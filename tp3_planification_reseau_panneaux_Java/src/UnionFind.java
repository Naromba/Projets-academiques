public class UnionFind {
    // parent[i] représente le parent du site i dans l'arbre.
    private int[] parent;
    // rank[i] représente la profondeur de l'arbre enraciné au site i.
    private int[] rank;

    // Constructeur qui initialise les tableaux parent et rank.
    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];

        // Au début, chaque site est son propre parent (il est le représentant de son ensemble)
        // et la profondeur de chaque arbre est 0.
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Méthode pour trouver le représentant (ou "racine") d'un site.
    // Utilise la compression de chemin pour aplatir la structure de l'arbre lors de la traversée.
    public int find(int site) {
        if (parent[site] != site) {
            parent[site] = find(parent[site]);
        }
        return parent[site];
    }

    // Méthode pour unir deux sites.
    // Utilise l'union par rang pour attacher l'arbre de rang inférieur à la racine de l'arbre de rang supérieur.
    public void union(int site1, int site2) {
        int root1 = find(site1);
        int root2 = find(site2);

        // Si les deux sites sont déjà dans le même ensemble, ne fait rien.
        if (root1 == root2) {
            return;
        }
        // Sinon, attache l'arbre de rang inférieur à la racine de l'arbre de rang supérieur.
        if (rank[root1] > rank[root2]) {
            parent[root2] = root1;
        } else if (rank[root1] < rank[root2]) {
            parent[root1] = root2;
        } else {
            parent[root2] = root1;
            // Si les deux arbres ont le même rang, fait de l'un d'eux une racine et augmente son rang de 1.
            rank[root1]++;
        }
    }
}
