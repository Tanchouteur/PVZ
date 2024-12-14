package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.ia.network.NeuralNetwork;

import java.util.*;
import java.util.concurrent.*;

public class GenerationManager {

    private final IAEnvironmentManager environmentManager;  // Gère les simulations d'IA
    private final ExecutorService executorService;  // Pour exécuter les simulations en parallèle

    private int generationNumber = 0;
    private double mutationAmplitude = 0.5;

    private int simulationPerGeneration = 1000;

    private final List<NeuralNetwork> bestModels = new ArrayList<>();
    private final List<NeuralNetwork> models = new ArrayList<>();  // Liste des modèles de réseaux neuronaux

    private boolean currentGenerationIsTrained = false;

    // Constructeur
    public GenerationManager(NeuralNetwork sourceModel) {

        this.executorService = Executors.newFixedThreadPool(/*Runtime.getRuntime().availableProcessors()*/4); // Pool de threads
        createNextGenerationFromOne(sourceModel);
        this.environmentManager = new IAEnvironmentManager(executorService);
    }

    // Méthode pour faire évoluer les modèles
    public void evolve() {
        if (currentGenerationIsTrained) {
            // Remplacer la génération actuelle avec la suivante
            System.out.println("Mutation de la génération " + generationNumber);
            this.createNextGenerationFromList();
            this.currentGenerationIsTrained = false;
        }

        System.out.println("Evolution de la génération " + generationNumber);
        this.environmentManager.initializeGames(this.models);

        while (!environmentManager.areAllSimulationsCompleted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Sélectionner les meilleurs modèles
        this.selectBestModels();

        int i = 0;
        System.out.println("\n=========== Best models ===========");
        for (NeuralNetwork model : this.bestModels) {
            System.out.println("Best model score: " + model.getScore() + " index: " + i++);
        }
        System.out.println("===================================\n");

        System.out.println("Generation size " + this.models.size());
        this.currentGenerationIsTrained = true;
        generationNumber++;
    }

    private void selectBestModels() {
        // Utilisation d'une PriorityQueue pour maintenir les meilleurs modèles sans trier à chaque fois
        System.out.println("Size of results list before sorting: " + this.models.size());

        PriorityQueue<NeuralNetwork> pq = new PriorityQueue<>(Comparator.comparingDouble(NeuralNetwork::getScore).reversed());
        pq.addAll(this.models);

        this.bestModels.clear();
        for (int i = 0; i < 5 && !pq.isEmpty(); i++) {
            this.bestModels.add(pq.poll());
        }
    }

    // Crée la prochaine génération de modèles en appliquant des mutations
    private void createNextGenerationFromList() {
        int numberOfMutants = this.simulationPerGeneration / 5;
        int numberOfRandom = (int) Math.round(this.simulationPerGeneration * 0.05); // 5% de la population

        this.models.clear();

        // Appliquer des mutations aux meilleurs modèles
        for (int i = 0; i < numberOfMutants; i++) {
            for (NeuralNetwork model : this.bestModels) {
                this.models.add(model.mutate(this.mutationAmplitude));
            }
        }

        // Ajout de modèles aléatoires
        for (int i = 0; i < numberOfRandom; i++) {
            this.models.add(new NeuralNetwork(new int[]{275, 180, 100, 52}));
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
        return (int) Math.round(mutationAmplitude * 100);
    }
}
