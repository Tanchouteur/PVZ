package fr.tanchou.pvz;

import fr.tanchou.pvz.game.PVZ;
import fr.tanchou.pvz.game.Player;
import fr.tanchou.pvz.guiJavaFx.PVZGraphic;
import fr.tanchou.pvz.ia.GenerationManager;
import fr.tanchou.pvz.ia.ModelSaver;
import fr.tanchou.pvz.ia.data.Statistics;
import fr.tanchou.pvz.ia.network.GameAI;

import java.util.Scanner;

import static java.lang.System.exit;

public class LauncherV2 {
    private final Player player;
    private final GenerationManager generationManager;
    private final Statistics statistics;
    private final Scanner scanner;

    public LauncherV2() {
        this.player = new Player("Louis");
        this.generationManager = new GenerationManager(ModelSaver.loadModel("best_model.json"));
        this.statistics = new Statistics();
        this.statistics.loadScoresHistoryFromFile("statistics.csv");
        this.statistics.printScoresHistory();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exitProgram = false;

        while (!exitProgram) {
            printMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 0 -> launchGameWithoutAI();
                case 1 -> launchGameWithAI();
                case 2 -> createRandomGeneration();
                case 3 -> trainModelsFromFile();
                case 4 -> evolveGeneration();
                case 5 -> displayStatistics();
                case 6 -> saveStatisticsToFile();
                case 7 -> loadStatisticsFromFile();
                case 8 -> exitProgram();
                default -> System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }

        System.out.println("Programme terminé. À bientôt !");
    }

    private void exitProgram() {
        saveStatisticsToFile();
        System.out.println("Programme terminé. À bientôt !");
        exit(0);
    }

    private void printMainMenu() {
        System.out.println("""

                Que voulez-vous faire ?
                0. Lancer le jeu en mode graphique sans IA
                1. Lancer le jeu en mode graphique avec IA
                2. Lancer une première génération random
                3. Entraîner les modèles à partir du fichier
                4. Faire évoluer la génération
                5. Afficher les statistiques
                6. Sauvegarder les statistiques dans un fichier
                7. Charger les statistiques depuis un fichier
                8. Quitter
                
                """);
    }

    private int getUserChoice() {
        System.out.print("Votre choix : ");
        while (!scanner.hasNextInt()) {
            System.out.println("Veuillez entrer un nombre valide.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void launchGameWithoutAI() {
        PVZ pvz = new PVZ(player);
        PVZGraphic.launchView(pvz);
        System.out.println("Fin du jeu");
        System.out.println("\nVotre score est de : " + player.calculateScore());
    }

    private void launchGameWithAI() {
        PVZ pvz = new PVZ(player, new GameAI(ModelSaver.loadModel("best_model.json")));
        PVZGraphic.launchView(pvz);
        System.out.println("\nVotre score est de : " + player.calculateScore());
    }

    private void createRandomGeneration() {
        System.out.println("\nCombien de simulations par génération ?");
        int simulations = scanner.nextInt();
        this.generationManager.setSimulationPerGeneration(simulations);
        System.out.println("Faire évoluer la génération ? (Oui/Non)");
        String evolve = scanner.next();
        if (evolve.equalsIgnoreCase("Oui")) {
            this.generationManager.evolve();
            statistics.saveScoresHistory(this.generationManager);
        } else {
            System.out.println("Fin de la génération.");
        }

    }

    private void trainModelsFromFile() {
        System.out.println("\nCombien de générations voulez-vous faire ?");
        int nbGenerations = scanner.nextInt();

        System.out.println("\nCombien de simulations par génération ?");
        int simulations = scanner.nextInt();
        this.generationManager.setSimulationPerGeneration(simulations);

        System.out.println("\nQuelle amplitude de mutation ? (entre 0.0 et 1.0)");
        double mutationAmplitude = scanner.nextDouble();
        this.generationManager.setMutationAmplitude(mutationAmplitude);

        for (int i = 0; i < nbGenerations; i++) {
            this.generationManager.evolve();
            statistics.saveScoresHistory(this.generationManager);
            System.out.println("\nGénération " + (i + 1) + " terminée\n");
        }

        this.statistics.printGlobalStatistics();

        System.out.println("Sauvegarder le meilleur modèle ? (Oui/Non)");
        String save = scanner.next();
        if (save.equalsIgnoreCase("Oui")) {
            ModelSaver.saveModel(this.generationManager.getBestModels().getFirst(), "best_model.json");
            saveStatisticsToFile();
        }

    }

    private void evolveGeneration() {
        if (generationManager == null) {
            System.out.println("Veuillez d'abord lancer une génération.");
            return;
        }
        generationManager.evolve();
        statistics.saveScoresHistory(generationManager);
        System.out.println("Génération évoluée avec succès.");
    }

    private void displayStatistics() {
        statistics.printAverageGenerationScore(generationManager.getAllModels());
        statistics.printScoresHistory();
        System.out.println("Amélioration par rapport à la génération précédente : " +
                (statistics.isCurrentGenerationBetter() ? "Oui" : "Non"));
    }

    private void saveStatisticsToFile() {
        statistics.saveHistoryToFile("statistics.csv");
        System.out.println("Statistiques sauvegardées dans " + "statistics.csv");
    }

    private void loadStatisticsFromFile() {
        statistics.loadScoresHistoryFromFile("statistics.csv");
        System.out.println("Statistiques chargées depuis " + "statistics.csv");
    }

    public static void main(String[] args) {
        new LauncherV2().start();
    }
}
