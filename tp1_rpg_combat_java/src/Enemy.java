/*
 * TRAVAIL PRATIQUE 1 - IFT1025
 * AUTEURS: NAROMBA CONDE , MATRICULE: 20251772
 * 			LALLIA DIAKITE, MATRICULE: 20256054
 * DATE : 13/02/2024
 * DESCRIPTION : ce programme permet la création d'un système de combat en Java, mettant en œuvre des classes de héros et d'ennemis,
 *               la gestion de l'expérience et du niveau. Le programme prend une phrase de quête en entrée et génère un résultat indiquant le succès
 *               ou l'échec de la quête.
 * */

public class Enemy extends Personnage {
	// Constructeur pour initialiser un ennemi avec des points de vie,
	// des points d'attaque et de l'expérience
	public Enemy(int health, int attackPoints, int experience) {
		super(health, attackPoints, experience);
	}

	public void levelUp(int healthEnemy, int attackPointsEnemy, int experienceEnemy) {
		health = healthEnemy;
		attackPoints = attackPointsEnemy;
		experience = experienceEnemy;
	}

}
