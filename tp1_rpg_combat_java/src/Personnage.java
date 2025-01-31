/*
 * TRAVAIL PRATIQUE 1 - IFT1025
 * AUTEURS: NAROMBA CONDE , MATRICULE: 20251772
 * 			LALLIA DIAKITE, MATRICULE: 20256054
 * DATE : 13/02/2024
 * DESCRIPTION : ce programme permet la création d'un système de combat en Java, mettant en œuvre des classes de héros et d'ennemis,
 *               la gestion de l'expérience et du niveau. Le programme prend une phrase de quête en entrée et génère un résultat indiquant le succès
 *               ou l'échec de la quête.
 * */

// Attributs protégés, accessibles dans les classes dérivées (Héros, Ennemi)
public class Personnage {
	protected int health;
	protected int attackPoints;
	protected int experience;

	// Constructeur pour initialiser un personnage
	public Personnage(int health, int attackPoints, int experience) {
		this.health = health;
		this.attackPoints = attackPoints;
		this.experience = experience;

	}

	// Méthode pour obtenir les points de vie du personnage
	public int getHealth() {
		return health;
	}

	// Méthode pour obtenir les points d'attaque du personnage
	public int getAttackPoints() {
		return attackPoints;
	}

	// Méthode pour obtenir l'expérience du personnage
	public int getExperience() {
		return experience;
	}

	// Méthode pour faire subir des dégâts au personnage et renvoyer
	// les points de vie restants
	public int haveDamage(int damage) {
		this.health -= damage;
		if (health < 0) {
			health = 0;
		}
		return health;
	}

	// Méthode pour déterminer les points d'attaque infligés par le personnage
	public int attack() {
		return attackPoints;
	}

}
