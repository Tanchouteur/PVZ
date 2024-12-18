package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.ia.network.NeuralNetwork;

import java.util.*;
import java.util.concurrent.*;

public class GenerationManager {

    private final IAEnvironmentManager environmentManager;  // Gère les simulations d'IA
    private ExecutorService executorService;  // Pour exécuter les simulations en parallèle

    private int generationNumber = 0;
    private double mutationAmplitude = 0.7;

    private int simulationPerGeneration = 500;

    private final List<NeuralNetwork> bestModels = new ArrayList<>();
    private final List<NeuralNetwork> models = new ArrayList<>();  // Liste des modèles de réseaux neuronaux

    private boolean currentGenerationIsTrained = false;

    private NeuralNetwork bestModelOverall;

    // Constructeur
    public GenerationManager(NeuralNetwork sourceModel) {
        this.bestModelOverall = sourceModel;
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // Pool de threads
        //this.createNextGenerationFromOne(sourceModel);
        this.environmentManager = new IAEnvironmentManager(executorService);
    }

    public void resetGenerationNumber() {
        this.generationNumber = 0;
    }

    // Méthode pour faire évoluer les modèles
    public void evolve() {

        if (this.bestModels.isEmpty()) {
            this.createNextGenerationFromOne(this.bestModelOverall);
        }

        // Calcul du score normalisé du meilleur modèle
        double bestScore = this.bestModels.isEmpty() ? 0 : this.bestModels.get(0).getScore();
        double maxScore = 17000;  // Score théorique maximal

        // Calcul du score moyen des modèles
        double averageScore = this.models.stream()
                .mapToDouble(NeuralNetwork::getScore)
                .average()
                .orElse(0);

        // Calcul de la mutation en fonction du score normalisé
        double bestToAverageRatio = bestScore / averageScore;
        System.out.println("Best Score: " + bestScore + ", Average Score: " + averageScore + ", Ratio: " + bestToAverageRatio);

        // Ajustement dynamique de l'amplitude de mutation
        /*if (this.generationNumber < 50) {
            // Plus de mutation au début pour explorer plus largement
            this.mutationAmplitude = Math.min(1.0, this.mutationAmplitude * 1.5);
        } else {*/
        // Réduire la mutation en fonction de l'amélioration des scores
        if (bestToAverageRatio < 1.5) {
            this.mutationAmplitude *= 1.2;  // Si les scores sont assez proches, augmenter la mutation
        } else {
            this.mutationAmplitude *= 0.8;  // Sinon, réduire pour mieux converger
        }
        //}

        // Limite de mutation (entre 0.1 et 1.0)
        this.mutationAmplitude = Math.max(0.1, Math.min(0.7, this.mutationAmplitude));

        System.out.println("Mutation amplitude ajustée: " + this.mutationAmplitude);

        // Préparation de la génération suivante
        if (currentGenerationIsTrained) {
            this.createNextGenerationFromList(mutationAmplitude);
            this.currentGenerationIsTrained = false;
        }

        System.out.println("Evolution de la génération " + this.generationNumber);
        this.environmentManager.initializeGames(this.models, false);

        // Attente de la fin de toutes les simulations
        while (!environmentManager.areAllSimulationsCompleted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Sélectionner les meilleurs modèles
        this.selectBestModels();

        // Affichage des meilleurs modèles
        int i = 0;
        System.out.println("\n=========== Best models ===========");
        for (NeuralNetwork model : this.bestModels) {
            System.out.println("Best model score: " + model.getScore() + " index: " + i++);
        }
        System.out.println("===================================\n");

        System.out.println("Generation size " + this.models.size());
        this.currentGenerationIsTrained = true;
        this.generationNumber++;
    }

    public void evolveSandbox(boolean random) {

        System.out.println("Evolution de la génération ");
        this.environmentManager.initializeGames(this.models, random);

        // Attente de la fin de toutes les simulations
        while (!environmentManager.areAllSimulationsCompleted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Sélectionner les meilleurs modèles
        this.selectBestModels();

        // Calcul du score normalisé du meilleur modèle
        double bestScore = this.bestModels.isEmpty() ? 0 : this.bestModels.get(0).getScore();

        // Calcul du score moyen des modèles
        double averageScore = this.models.stream()
                .mapToDouble(NeuralNetwork::getScore)
                .average()
                .orElse(0);

        // Calcul de la mutation en fonction du score normalisé
        double bestToAverageRatio = bestScore / averageScore;
        System.out.println("Best Score: " + bestScore + ", Average Score: " + averageScore + ", Ratio: " + bestToAverageRatio);
        // Affichage des meilleurs modèles
        int i = 0;
        System.out.println("\n=========== Best models ===========");
        for (NeuralNetwork model : this.bestModels) {
            System.out.println("Best model score: " + model.getScore() + " index: " + i++);
        }
        System.out.println("===================================\n");

        System.out.println("Generation size " + this.models.size());
    }

    private void selectBestModels() {
        System.out.println("Size of results list before sorting: " + this.models.size());

        PriorityQueue<NeuralNetwork> pq = new PriorityQueue<>(Comparator.comparingDouble(NeuralNetwork::getScore).reversed());
        pq.addAll(this.models);

        this.bestModels.clear();
        for (int i = 0; i < 10 && !pq.isEmpty(); i++) {
            this.bestModels.add(pq.poll());
        }

        if (this.bestModels.getFirst().getScore() <= 1){
            System.out.println("Best model score is too low, creating a new random generation");

        }

        if (this.bestModels.getFirst().getScore() > this.bestModelOverall.getScore()) {
            this.bestModelOverall = this.bestModels.getFirst();
        }
    }

    // Crée la prochaine génération de modèles en appliquant des mutations
    public void createNextGenerationFromList(double mutationAmplitude) {
        int numberOfMutants = this.simulationPerGeneration / this.bestModels.size();  // Nombre de mutations par meilleur modèle
        int numberOfRandom = (int) Math.round(this.simulationPerGeneration * 0.05); // 5% de la population
        this.mutationAmplitude = mutationAmplitude;
        this.models.clear();

        // Appliquer des mutations aux meilleurs modèles
        for (int i = 0; i < numberOfMutants; i++) {
            for (NeuralNetwork model : this.bestModels) {
                this.models.add(model.mutate(this.mutationAmplitude));
            }
        }

        this.models.addAll(this.bestModels);

        // Ajout de modèles aléatoires
        for (int i = 0; i < numberOfRandom; i++) {
            this.models.add(new NeuralNetwork(new int[]{230, 256, 128, 64, 52}));
        }

        if (this.bestModels.getFirst().getScore() != this.bestModelOverall.getScore()){
            this.models.add(this.bestModelOverall.cloneNetwork());
        }
    }

    // Crée la première génération de modèles en appliquant des mutations
    private void createNextGenerationFromOne(NeuralNetwork sourceModel) {
        System.out.println("Création de la première génération de cette session : " + this.simulationPerGeneration + " mutations");
        for (int i = 0; i < this.simulationPerGeneration; i++) {
            this.models.add(sourceModel.mutate(this.mutationAmplitude));  // Clone et applique une mutation
        }
    }

    public void setSimulationPerGeneration(int i) {
        this.simulationPerGeneration = i;
    }

    public void setMutationAmplitude(double mutationAmplitude) {
        this.mutationAmplitude = mutationAmplitude;
    }

    public List<NeuralNetwork> getBestModels() {
        return bestModels;
    }

    public List<NeuralNetwork> getAllModels() {
        return this.models;
    }

    public int getMutationAmplitude() {
        return (int) (mutationAmplitude * 100);
    }

    public void createRandomGeneration() {
        this.models.clear();
        for (int i = 0; i < this.simulationPerGeneration; i++) {
            this.models.add(new NeuralNetwork(new int[]{230, 256, 128, 64, 52}));
        }
    }

    public void setNumberOfThreads(int nbThreads) {
       this.executorService = Executors.newFixedThreadPool(nbThreads);
       this.environmentManager.setExecutorService(this.executorService);
    }

    public NeuralNetwork getBestModelOverall() {
        return bestModelOverall;
    }

    public int getSimulationPerGeneration() {
        return this.simulationPerGeneration;
    }
}
