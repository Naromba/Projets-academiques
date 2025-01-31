
public class Pays implements Comparable<Pays> {

	private int code;
	private String nom, capitale;
	private double superficie;
	private int population;

	public Pays(int code, String nom, String capitale, double superficie, int population) {
		this.code = code;
		this.nom = nom;
		this.capitale = capitale;
		this.superficie = superficie;
		this.population = population;
	}

	public String getNom() {
		return nom;
	}

	public int getPopulation() {
		return population;
	}

	public String getCapitale() {
		return capitale;
	}

	public int getCode() {
		return code;
	}

	public String toString() {
		return String.format("%d  %30s %25s %14.0f %14d\n", code, nom, capitale, superficie, population);

	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Pays))
			return false;
		else if (this == obj)
			return true;
		else {
			Pays autre = (Pays) obj;
			return nom.trim().equalsIgnoreCase(autre.nom.trim());
		}

	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setPopulation() {
		this.population *= 10;
	}

	@Override
	public int compareTo(Pays autre) {
		return nom.toUpperCase().trim().compareTo(autre.nom.toUpperCase().trim());
	}
}
