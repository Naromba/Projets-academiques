/*
 * TRAVAIL PRATIQUE 1 - IFT1025
 * AUTEURS: NAROMBA CONDE , MATRICULE: 20251772
 * 			LALLIA DIAKITE, MATRICULE: 20256054
 * DATE : 13/02/2024
 * DESCRIPTION : ce programme permet la création d'un système de combat en Java, mettant en œuvre des classes de héros et d'ennemis,
 *               la gestion de l'expérience et du niveau. Le programme prend une phrase de quête en entrée et génère un résultat indiquant le succès
 *               ou l'échec de la quête.
 * */

public class Hero extends Personnage {
	private String name;
	private int level;
	private int maxHealth;
	private final int maxLevel = 99;

	//// Constructeur pour initialiser un héros avec un name,
	// des points de vie et des points d'attaque
	public Hero(String name, int health, int attackPoints) {
		super(health, attackPoints, 0);
		this.name = name;
		this.level = 1;
		this.maxHealth = health;
	}

	// Méthode pour obtenir le name du héros
	public String getName() {
		return name;
	}

	// Méthode pour obtenir le niveau du héros
	public int getLevel() {
		return level;
	}

	// Redéfinition de la méthode d'obtention de l'expérience du héros
	public int getExperience() {
		return experience;

	}

	// Méthode pour obtenir le point de vie maximale du héros
	public int getMaxHealth() {
		return maxHealth;
	}

	// Méthode pour vérifier si le héros a atteint le niveau maximal
	public boolean haveMaxLevel() {
		return level >= maxLevel;
	}

	// Méthode pour obtenir le niveau maximal possible
	public int getMaxLevel() {
		return maxLevel;
	}

	// Redéfinition de la méthode pour subir des dégâts
	// en fonction du type de héros
	@Override
	public int haveDamage(int damage) {
		if (name.charAt(0) == 'A') {
			health -= damage * 2;
		} else if (name.charAt(0) == 'D') {
			health -= damage / 2;
		} else {
			health -= damage;
		}
		if (health < 0) {
			health = 0;
		}
		return health;

	}

	// Méthode pour reposer le héros,
	// en rétablissant ses points de vie au maximum
	public void rest() {
		this.health = maxHealth;
	}

	// Méthode pour soigner le héros en ajoutant des points de vie
	public void heal(int healthPoints) {
		this.health += healthPoints;
		// Assurer que les points de vie ne dépassent pas la santé maximale
		if (this.health > this.maxHealth) {
			this.health = this.maxHealth;
		}
	}

	// Méthode pour permettre au héros de s'entraîner et
	// augmenter ses points d'attaque
	public void train(int attackPoints) {
		this.attackPoints += attackPoints;
	}

	// Redéfinition de la méthode pour infliger une attaque
	// en fonction du type de héros
	@Override
	public int attack() {
		if (name.charAt(0) == 'A') {
			return attackPoints * 2;
		} else if (name.charAt(0) == 'D') {
			return attackPoints / 2;

		} else {
			return attackPoints;
		}
	}

	// Méthode qui definie les ameliorations du hero
	// lorsqu'il passe au niveau suivant
	private void nextLevel() {
		level += 1;
		experience = 0;
		maxHealth += 12;
		health = maxHealth;
		attackPoints += 6;
	}

	// Méthode qui calcule l'expérience requise pour passer au niveau suivant
	private int requiredExperience() {
		int requiredExp = (int) Math.ceil((double) 50 + (level + 1) * 20 * Math.pow(1.1, level + 1));
		return requiredExp;
	}

	// Méthode qui permet au héros de gagner de l'expérience
	public void gainExperience(int point) {
		experience += point;
		if (experience >= requiredExperience() && level <= maxLevel) {
			nextLevel();
		}

	}

}
