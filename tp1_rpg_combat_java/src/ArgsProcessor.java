/*
 * TRAVAIL PRATIQUE 1 - IFT1025
 * AUTEURS: NAROMBA CONDE , MATRICULE: 20251772
 * 			LALLIA DIAKITE, MATRICULE: 20256054
 * DATE : 13/02/2024
 * DESCRIPTION : ce programme permet la création d'un système de Kombat en Java, mettant en œuvre des classes de héros et d'enemys,
 *               la gestion de l'expérience et du niveau. Le programme prend une phrase de quête en entrée et génère un résultat indiquant le succès
 *               ou l'échec de la quête.
 * */

public class ArgsProcessor {
	private static int defeatedEnemy = 0;
	private static int healthEnemy = 100;
	private static int attackPointsEnemy = 25;
	private static int experienceEnemy = 35;

	// Méthode principale pour traiter la phrase d'entrée et afficher le résultat
	public static void process(String[] args) {
		String[] phrase = makePhrase(args[0]);

		// Création d'une instance d'enemy avec des valeurs initiales
		Enemy enemy = new Enemy(healthEnemy, attackPointsEnemy, experienceEnemy);

		// Création d'une instance de héros avec les informations fournies dans la
		// phrase
		Hero hero = new Hero(phrase[0], Integer.parseInt(phrase[1]), Integer.parseInt(phrase[2]));

		// Parcours des actions spécifiées dans la phrase en input
		for (int i = 3; i < phrase.length; i++) {
			if (!doAction(phrase[i], hero, enemy)) {
				break; // Le héros ne survit pas à l'action, arrêter la boucle
			}

			if (hero.getHealth() > 0 && hero.haveMaxLevel()) {
				break; //// Le héros a atteint le niveau maximal, arrêter la boucle
			}

		}

		// Affichage du résultat en fonction de la survie du héros
		if (hero.getHealth() > 0) {
			System.out.println("In his quest, " + hero.getName() + " beat " + defeatedEnemy + " enemies, attained level "
							+ hero.getLevel() + " and survived with " + hero.getHealth() + " HP!");
		} else {
			System.out.println("In his quest, " + hero.getName() + " died after beating " + defeatedEnemy
					+ " enemies and attaining level " + hero.getLevel() + "!");
		}

	}

	// Méthode pour simuler un Kombat entre le héros et un enemy
	private static void Kombat(Hero hero, Enemy enemy) {

		while (true) {
			// Le héros attaque l'enemy
			if (enemy.haveDamage(hero.attack()) == 0) {
				break; // L'enemy est vaincu, arrêter le Kombat
			}

			// L'enemy attaque le héros
			if (hero.haveDamage(enemy.attack()) == 0) {
				break; // Le héros est vaincu, arrêter le Kombat
			}

		}
	}

	// Méthode pour diviser la phrase en parties distinctes
	private static String[] makePhrase(String args) {
		return args.trim().split(",");
	}

	// méthode qui prend la partie de la phrase qui décrit l'action et le héros,
	// puis effectue l'action correspondante
	// retourne true si le joueur survit à l'action, false sinon

	private static boolean doAction(String action, Hero hero, Enemy enemy) { // voir si on doit rajouter le parametre
																				// enemy ici
		// Transformation du String action en un tableau de String, en séparant les mots
		// par des espaces
		String[] phrase = action.trim().split(" ");

		// Le type d'action est déterminé par le premier mot de la phrase
		switch (phrase[0]) {
		case "fought":
			// "fought n enemies"
			for (int i = 0; i < Integer.parseInt(phrase[1]); i++) {
				// Deroulement d'un combat d'un avec l'enemy
				Kombat(hero, enemy);

				// Vérification si le héros a été tué pendant le Kombat
				if (hero.getHealth() == 0) {
					return false;
				}

				// Vérification si l'enemy a été vaincu pendant le combat
				if (enemy.getHealth() == 0) {
					defeatedEnemy++;
					// Mise à jour des statistiques de l'enemy et level up de enemy
					hero.gainExperience(experienceEnemy);
					healthEnemy += 10;
					attackPointsEnemy += 5;
					experienceEnemy += 8;
					enemy.levelUp(healthEnemy, attackPointsEnemy, experienceEnemy);

				}

			}
			break;
		case "rested":
			// rested
			hero.rest();
			break;
		case "healed":
			// healed n health points
			int healthPoints = Integer.parseInt(phrase[1]);
			hero.heal(healthPoints);
			break;
		case "trained":
			// trained to get n attack points
			int attackPoints = Integer.parseInt(phrase[3]);
			hero.train(attackPoints);
			break;
		}

		return true;
	}

}
