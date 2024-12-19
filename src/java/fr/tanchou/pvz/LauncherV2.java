package fr.tanchou.pvz;

import fr.tanchou.pvz.game.PVZ;
import fr.tanchou.pvz.game.Player;
import fr.tanchou.pvz.guiJavaFx.PVZGraphic;
import fr.tanchou.pvz.ia.GenerationManager;
import fr.tanchou.pvz.ia.data.ModelSaver;
import fr.tanchou.pvz.ia.data.Statistics;
import fr.tanchou.pvz.ia.network.GameAI;
import fr.tanchou.pvz.ia.network.NeuralNetwork;

import java.util.Scanner;

import static java.lang.System.exit;

public class LauncherV2 {
    private final Player player;
    private final GenerationManager generationManager;
    private final Statistics statistics;
    private final Scanner scanner;

    public LauncherV2() {
        NeuralNetwork model = ModelSaver.loadModel("Models/best_model.json");

        this.player = new Player("Louis");
        this.generationManager = new GenerationManager(model);
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
                case 3 -> automaticTrainModel();
                case 4 -> semiAutomaticTrainModel();
                case 5 -> manualTrainModel();
                case 6 -> displayStatistics();
                case 7 -> saveStatisticsToFile();
                case 8 -> loadStatisticsFromFile();
                case 9 -> changeNumberOfThreads();
                case 10 -> exitProgram();
                default -> System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }

        System.out.println("Programme terminé. À bientôt !");
    }

    private void printMainMenu() {
        System.out.println("""

                ============ Que voulez-vous faire ? ============
                0. Lancer le jeu en mode graphique sans IA
                1. Lancer le jeu en mode graphique avec IA
                2. Lancer une première génération random
                3. Entraîner automatiquement un modèle
                4. Entraîner semi automatiquement un modèle
                5. Entrainer manuellement un modèle
                6. Afficher les statistiques
                7. Sauvegarder les statistiques dans un fichier
                8. Charger les statistiques depuis un fichier
                9. Changer le nombre de threads
                10. Quitter
                
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
        try {
            System.out.println("Avec aléatoire ? (true/false)");
            boolean random = scanner.nextBoolean();
            PVZ pvz = new PVZ(player, random);
            PVZGraphic.launchView(pvz);
            System.out.println("\nVotre score est de : " + player.calculateScore());
        }catch (Exception e) {
            System.out.println("Problème dans le jeu sans IA : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void launchGameWithAI() {
        System.out.println("A quel vitesse voulez-vous que le model joue ? (multiplicateur)");
        double speed = scanner.nextDouble();

        System.out.println("Avec aléatoire ? (true/false)");
        boolean random = scanner.nextBoolean();

        System.out.println("Chemin du model à charger (laisser vide pour charger le model par défaut) : ");
        String loadingPath = scanner.next();

        NeuralNetwork model = null;
        if (!loadingPath.equalsIgnoreCase("")) {
            model = ModelSaver.loadModel(loadingPath);
            if (model == null) {
                System.err.println("Impossible de charger le model : " + loadingPath);
            }
        }else {
            System.out.println("Chargement du model par défaut : best_model.json");
            model = ModelSaver.loadModel("best_model.json");
        }

        System.out.println("Lancement du jeu avec IA...");

        try {
            PVZ pvz = new PVZ(player, new GameAI(model), speed, random);

            PVZGraphic.launchView(pvz);
            System.out.println("\nSont score est de : " + player.calculateScore());
        }catch (Exception e){
            System.out.println("Problème dans le menu de lancement du jeu avec IA : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createRandomGeneration() {

        System.out.println("\nCombien de simulations par génération ?");
        int simulations = scanner.nextInt();
        this.generationManager.setSimulationPerGeneration(simulations);

        System.out.println("Faire évoluer la génération ? (Oui/Non)");
        String evolve = scanner.next();

        this.generationManager.createRandomGeneration();

        if (evolve.equalsIgnoreCase("Oui")) {
            System.out.println("\nCombien de générations voulez-vous faire ?");
            int nbGenerations = scanner.nextInt();

            this.generationManager.resetGenerationNumber();

            for (int i = 0; i < nbGenerations; i++) {
                this.generationManager.trainModel();
                statistics.saveScoresHistory(this.generationManager);
                System.out.println("\nGénération " + (i + 1) + " terminée\n");
            }

            statistics.saveScoresHistory(this.generationManager);
        } else {
            System.out.println("Fin de la génération.");
        }

        //ModelSaver.saveModel(this.generationManager.getBestModels().getFirst(), "best_model.json");

    }

    private void automaticTrainModel() {
        System.out.println("\nCombien de simulations par génération ? (par défaut : " + this.generationManager.getSimulationPerGeneration() + ") ");
        try {
            this.generationManager.setSimulationPerGeneration(Integer.parseInt(scanner.next()));
        }catch (Exception e){
            System.out.println("Utilisation de la valeur par défaut.");
        }

        System.out.println("\nQuelle amplitude de mutation de départ ? (par défaut : " + this.generationManager.getMutationAmplitude() + ") ");
        try {
            this.generationManager.setMutationAmplitude(Double.parseDouble(scanner.next()));
        }catch (Exception e){
            System.out.println("Utilisation de la valeur par défaut.");
        }

        this.generationManager.resetGenerationNumber();

        this.generationManager.fullAutoTrain();

        this.statistics.printGlobalStatistics();

        System.out.println("Sauvegarder le meilleur modèle ? (Oui/Non)");
        String save = scanner.next();
        if (save.equalsIgnoreCase("Oui") || save.equalsIgnoreCase("o") || save.equalsIgnoreCase("Y") || save.equalsIgnoreCase("Yes") || save.equalsIgnoreCase("1")){
            ModelSaver.saveModel(this.generationManager.getBestModelOverall(), "best_model.json");
            saveStatisticsToFile();
        }
    }

    private void semiAutomaticTrainModel() {
        System.out.println("\nCombien de générations voulez-vous faire ? ");
        int nbGenerations = scanner.nextInt();

        System.out.println("\nCombien de simulations par génération ? (par défaut : " + this.generationManager.getSimulationPerGeneration() + ") ");
        try {
            this.generationManager.setSimulationPerGeneration(Integer.parseInt(scanner.next()));
        }catch (Exception e){
            System.out.println("Utilisation de la valeur par défaut.");
        }

        System.out.println("\nQuelle amplitude de mutation de départ ? (par défaut : " + this.generationManager.getMutationAmplitude() + ") ");
        try {
            this.generationManager.setMutationAmplitude(Double.parseDouble(scanner.next()));
        }catch (Exception e){
            System.out.println("Utilisation de la valeur par défaut.");
        }

        this.generationManager.resetGenerationNumber();

        for (int i = 0; i < nbGenerations; i++) {

            this.generationManager.trainModel();

            statistics.saveScoresHistory(this.generationManager);
            System.out.println("\nGénération " + (i + 1) + " terminée\n");
        }

        this.statistics.printGlobalStatistics();

        System.out.println("Sauvegarder le meilleur modèle ? (Oui/Non)");
        String save = scanner.next();
        if (save.equalsIgnoreCase("Oui") || save.equalsIgnoreCase("o") || save.equalsIgnoreCase("Y") || save.equalsIgnoreCase("Yes") || save.equalsIgnoreCase("1")){
            ModelSaver.saveModel(this.generationManager.getBestModelOverall(), "best_model.json");
            saveStatisticsToFile();
        }
    }

    private void manualTrainModel() {
        boolean continueEvolution = true;
        while (continueEvolution){
            System.out.println("\nRemplacer la génération en la mutant ? (1 / 0)");
            int newGen = scanner.nextInt();

            System.out.println("Avec aléatoire ? (true/false)");
            boolean random = scanner.nextBoolean();

            if (newGen == 1){
                System.out.println("\nCombien de simulations par génération ? actuellement : " + generationManager.getSimulationPerGeneration());
                try {
                    this.generationManager.setSimulationPerGeneration(Integer.parseInt(scanner.next()));
                }catch (Exception e){
                    System.out.println("Utilisation de la valeur par défaut.");
                }

                System.out.println("\nQuelle amplitude de mutation ? (actuellement : " + generationManager.getMutationAmplitude() + ") ");
                try {
                    this.generationManager.setMutationAmplitude(Double.parseDouble(scanner.next()));
                }catch (Exception e){
                    System.out.println("Utilisation de la valeur par défaut.");
                }
            }

            generationManager.trainSandbox(random);

            statistics.saveScoresHistory(generationManager);
            System.out.println("Génération évoluée avec succès.");

            //ask for save best model
            System.out.println("Sauvegarder le meilleur modèle ? (Oui/Non)");
            String save = scanner.next();
            if (save.equalsIgnoreCase("oui") || save.equalsIgnoreCase("o") || save.equalsIgnoreCase("y") || save.equalsIgnoreCase("yes") || save.equalsIgnoreCase("1")){
                System.out.println("Nom du fichier (exemple : best_model) : ");
                String fileName = scanner.next();
                ModelSaver.saveModel(this.generationManager.getBestModelOverall(), fileName + ".json");

                this.saveStatisticsToFile();
            }

            System.out.println("\nCréez une nouvelle génération ? (1 / 0)");
            int newEvolve = scanner.nextInt();

            if (newEvolve == 0){
                continueEvolution = false;
            }
        }
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

    private void changeNumberOfThreads() {
        System.out.println("Combien de threads voulez-vous utiliser ? actuellement : " + this.generationManager.getNumberOfThreads());
        int nbThreads = scanner.nextInt();
        this.generationManager.setNumberOfThreads(nbThreads);
    }

    private void exitProgram() {
        saveStatisticsToFile();
        System.out.println("Programme terminé. À bientôt !");
        exit(0);
    }

    public static void main(String[] args) {
        new LauncherV2().start();
    }
}
