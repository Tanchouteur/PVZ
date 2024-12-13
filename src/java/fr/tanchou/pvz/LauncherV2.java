package fr.tanchou.pvz;

import fr.tanchou.pvz.game.PVZ;
import fr.tanchou.pvz.game.Player;
import fr.tanchou.pvz.guiJavaFx.PVZGraphic;
import fr.tanchou.pvz.ia.GenerationManager;
import fr.tanchou.pvz.ia.ModelSaver;
import fr.tanchou.pvz.ia.network.GameAI;

import java.util.Scanner;

public class LauncherV2 {
    public static void main(String[] args) {
        //Main qui scanne l'input de la console pour qu'on puisse choisir quoi faire
        PVZ pvz;
        GenerationManager generationManager = null;
        while (true) {
            System.out.println("Que voulez-vous faire ?\n0. Lancer le jeu en mode graphique sans IA\n" +
                    "1. Lancer le jeu en mode graphique avec IA\n" +
                    "2. Lancer une premiere génération random\n" +
                    "3. Lancer le jeu en mode IA train\n" +
                    "4. Faire une evolution\n" +
                    "5. Quitter");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();



            switch (choice) {

                case 0:
                    pvz = new PVZ(new Player("Louis"));
                    PVZGraphic.launchView(pvz);
                    break;

                case 1:
                    pvz = new PVZ(new Player("IA"), new GameAI(ModelSaver.loadModel("best_model.json")));
                    PVZGraphic.launchView(pvz);
                    break;

                case 2:
                    System.out.println("Création de la première génération random");
                    generationManager = new GenerationManager(false);
                    System.out.println("Faire Evoluer la génération ? (Oui/Non)");
                    String evolve = scanner.next();
                    if (evolve.equalsIgnoreCase("Oui")) {
                        generationManager.evolve();
                    }else {
                        System.out.println("Fin de la génération");
                    }
                    break;

                case 3:
                    generationManager = new GenerationManager(true);

                    System.out.println("Combien de génération voulez-vous faire ?");
                    int nbGenerations = scanner.nextInt();

                    System.out.println("Combien de simulations par génération ?");
                    generationManager.setSimulationPerGeneration(scanner.nextInt());

                    for (int i = 0; i < nbGenerations; i++) {
                        generationManager.evolve();
                        System.out.println("Génération " + i + " terminée");
                    }

                    break;

                case 4:
                    if (generationManager == null) {
                        System.out.println("Veuillez d'abord lancer une génération");
                        break;
                    }
                    generationManager.evolve();
                    break;

                case 5:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Choix invalide");
                    break;
            }
        }

    }
}
