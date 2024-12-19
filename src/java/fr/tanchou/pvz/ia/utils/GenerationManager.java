package fr.tanchou.pvz.ia.utils;

import fr.tanchou.pvz.ia.data.ModelSaver;
import fr.tanchou.pvz.ia.network.NeuralNetwork;

import java.util.*;

public class GenerationManager {

    private final IAEnvironmentManager environmentManager;
    private int nbThreadToUse = 8;

    private final int[] networkLayers = new int[]{230, 256, 128, 64, 52};

    private final List<NeuralNetwork> models = new ArrayList<>();  // Liste des modèles de réseaux neuronaux
    private final List<NeuralNetwork> bestModels = new ArrayList<>();

    private NeuralNetwork bestModelOverall;

    private int generationNumber = 0;
    private double mutationAmplitude = 1.0;
    private int simulationPerGeneration = 5000;
    private boolean currentGenerationIsTrained = false;

    // Variables pour le calcul de la mutation ET statistiques
    private int deltaScore = 3000;
    private int currentAverageScore = 0;
    private int previousAverageScore = 0;

    private boolean randomLevel = false;

    private int repereLast10Gen = 0;

    // Constructeur
    public GenerationManager(NeuralNetwork sourceModel) {
        this.bestModelOverall = sourceModel;

        if (this.bestModelOverall == null) {
            this.bestModelOverall = new NeuralNetwork(this.networkLayers);
        }

        this.environmentManager = new IAEnvironmentManager();
    }

    public void fullAutoTrain() {
        while (deltaScore > 100 || !this.randomLevel) {
            this.trainModel();
            if (this.generationNumber % 10 == 0) {
                ModelSaver.saveModel(this.bestModelOverall, "bestModelOverall");
                if (this.repereLast10Gen == 0){
                    this.repereLast10Gen = this.currentAverageScore;
                } else {
                    if (this.currentAverageScore - this.repereLast10Gen < 100){
                        break;
                    }
                }
            }
        }
    }

    // Méthode pour faire évoluer les modèles
    public void trainModel() {
        System.out.println("========= Génération " + this.generationNumber + " =========");
        if (this.bestModels.isEmpty()) {
            this.createNextGenerationFromOne(this.bestModelOverall);
        }

        // Préparation de la génération suivante
        if (currentGenerationIsTrained) {
            this.createNextGenerationFromList(mutationAmplitude);
            this.currentGenerationIsTrained = false;
        }

        this.chooseRandomLevel();

        this.evolve(this.randomLevel);

        // Sélectionner les meilleurs modèles
        this.selectBestModels();

        this.computeStatistics();

        this.printBestModels();

        System.out.println("========= Fin de la génération " + this.generationNumber + " =========");
    }

    public void trainSandbox(boolean random) {

        if (this.bestModels.isEmpty()) {
            this.createRandomGeneration();
        }

        this.evolve(random);

        this.selectBestModels();

        this.computeStatistics();

        this.printBestModels();
    }

    //méthode pour faire évoluer les modèles
    public void evolve(boolean random) {

        System.out.println("========= Evolution de la génération " + this.generationNumber + ", avec niveau aléatoire: " + random + " =========");
        this.environmentManager.initializeGames(this.models, random, this.nbThreadToUse);

        // Attente de la fin de toutes les simulations
        while (!environmentManager.areAllSimulationsCompleted()) {
            System.out.println("Simulation advancement : " + environmentManager.getPrcntgOfSimulationsCompleted());
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        this.currentGenerationIsTrained = true;

        System.out.println("============ Fin d'évolution ============");
    }

    private void chooseRandomLevel() {
        this.randomLevel = !this.randomLevel && this.generationNumber > 10 && this.deltaScore < 150 && this.bestModels.get(1).getScore() > 12000;
    }

    private void computeStatistics() {

        // Calcul du score normalisé du meilleur modèle
        int bestScore = this.bestModels.isEmpty() ? 0 : (int) this.bestModels.get(0).getScore();
        //double maxOptimalScore = 17000;  // Score théorique maximal

        // Calcul du score moyen des modèles
        this.currentAverageScore = (int) this.models.stream()
                .mapToDouble(NeuralNetwork::getScore)
                .average()
                .orElse(0);

        // Calcul du delta de score
        this.deltaScore = (this.currentAverageScore - this.previousAverageScore);

        this.previousAverageScore = this.currentAverageScore;

        // Calcul de la mutation en fonction du score normalisé
        this.computeMutationAmplitude(bestScore, this.currentAverageScore);

        double bestToAverageRatio = (double) bestScore / this.currentAverageScore;
        System.out.println("=========== Statistiques ===========");
        System.out.println("Best Score: " + bestScore + ", Average Score: " + this.currentAverageScore + ", Ratio best/average: " + bestToAverageRatio + ", " +
                "\ndelta with previous gen :" + this.deltaScore + ", Mutation amplitude: " + this.mutationAmplitude);

    }

    private void computeMutationAmplitude(double bestScore, double averageScore) {
        // Normalisation des scores par rapport au maximum attendu (18 000)
        double maxScore = 18000.0;
        double normalizedBestScore = bestScore / maxScore;
        double normalizedAverageScore = averageScore / maxScore;

        // Calcul du ratio
        double bestToAverageRatio = normalizedBestScore / normalizedAverageScore;

        // Décroissance exponentielle contrôlée
        double initialAmplitude = 2.0; // Amplitude de départ
        double lambda = 0.1; // Contrôle la vitesse de décroissance
        double baseAmplitude = initialAmplitude * Math.exp(-lambda * this.generationNumber);

        // Ajustement en fonction du ratio normalisé
        if (bestToAverageRatio < 1.2) {
            baseAmplitude *= 1.3; // Exploration accrue si les scores sont proches
        } else if (bestToAverageRatio > 1.8) {
            baseAmplitude *= 0.7; // Réduction si domination trop forte des meilleurs
        }

        // Ajustement en fonction du delta de score
        if (this.deltaScore < 100) { // Peu de progression
            baseAmplitude *= 1.1;
        } else if (this.deltaScore > 500) { // Bonne progression
            baseAmplitude *= 0.9;
        }

        // Ajustement selon la performance actuelle (exemple basé sur 10k+)
        if (bestScore > 10000 && bestScore < maxScore * 0.9) {
            baseAmplitude *= 1.05; // Encourage la mutation si proche du plafond
        }

        // Plafonnement réaliste
        this.mutationAmplitude = Math.max(0.005, Math.min(baseAmplitude, 2.0));
    }

    private void selectBestModels() {

        PriorityQueue<NeuralNetwork> pq = new PriorityQueue<>(Comparator.comparingDouble(NeuralNetwork::getScore).reversed());
        pq.addAll(this.models);

        this.bestModels.clear();
        for (int i = 0; i < 10 && !pq.isEmpty(); i++) {
            this.bestModels.add(pq.poll());
        }

        if (this.bestModels.getFirst().getScore() <= 100){
            System.err.println("Best model score is too low, creating a new random generation");
        }

        if (this.bestModels.getFirst().getScore() > this.bestModelOverall.getScore()) {
            this.bestModelOverall = this.bestModels.getFirst();
        }
    }

    private void printBestModels() {
        // Affichage des meilleurs modèles
        System.out.println("\n=========== Best models ===========");
        for (NeuralNetwork model : this.bestModels) {
            System.out.println("Best model score: " + model.getScore());
        }
        System.out.println("===================================\n");

        System.out.println("Generation size " + this.models.size());
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
            this.models.add(new NeuralNetwork(this.networkLayers));
        }

        if (this.bestModels.getFirst().getScore() != this.bestModelOverall.getScore()){
            this.models.add(this.bestModelOverall.cloneNetwork());
        }

        this.generationNumber++;
    }

    // Crée la première génération de modèles en appliquant des mutations
    private void createNextGenerationFromOne(NeuralNetwork sourceModel) {
        System.out.println("Création de la première génération de cette session : " + this.simulationPerGeneration + " mutations");
        for (int i = 0; i < this.simulationPerGeneration; i++) {
            this.models.add(sourceModel.mutate(this.mutationAmplitude));  // Clone et applique une mutation
        }
    }

    public void createRandomGeneration() {
        this.models.clear();
        for (int i = 0; i < this.simulationPerGeneration; i++) {
            this.models.add(new NeuralNetwork(new int[]{230, 256, 128, 64, 52}));
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

    public void setNumberOfThreads(int nbThreads) {
        this.nbThreadToUse = nbThreads;
    }

    public NeuralNetwork getBestModelOverall() {
        return bestModelOverall;
    }

    public int getSimulationPerGeneration() {
        return this.simulationPerGeneration;
    }

    public void resetGenerationNumber() {
        this.generationNumber = 0;
    }

    public int getNumberOfThreads() {
        return this.nbThreadToUse;
    }
}
