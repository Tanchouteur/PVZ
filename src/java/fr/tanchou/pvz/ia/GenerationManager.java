package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.ia.network.NeuralNetwork;

import java.util.*;

public class GenerationManager {

    private IAEnvironmentManager environmentManager;  // Gère les simulations d'IA

    private int generationNumber = 0;
    private double mutationAmplitude = 0.5;

    private int simulationPerGeneration = 4000;

    private List<NeuralNetwork> bestModels = new ArrayList<>();
    private final List<NeuralNetwork> models = new  ArrayList<>();  // Liste des modèles de réseaux neuronaux

    private boolean currentGenerationIsTrained = false;

    // Constructeur
    public GenerationManager(NeuralNetwork sourceModel) {
        createNextGenerationFromOne(sourceModel);
        this.environmentManager = new IAEnvironmentManager();
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
        this.environmentManager = new IAEnvironmentManager();
        this.environmentManager.initializeGames(this.models);

        while (!environmentManager.areAllSimulationsCompleted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        this.environmentManager.stopSimulations();  // Arrêter les simulations

        // Sélectionner les meilleurs modèles
        this.selectBestModels();

        int i = 0;
        System.out.println("\n=========== Best models ===========");
        for (NeuralNetwork model : this.bestModels) {
            System.out.println("Best model score: " + model.getScore() + " index: " + i);
            i++;
        }
        System.out.println("===================================\n");

        System.out.println("Generation size " + this.models.size());
        this.currentGenerationIsTrained = true;
        generationNumber++;
    }

    private void selectBestModels() {
        System.out.println("Size of results list before sorting: " + this.models.size());

        this.models.sort((r1, r2) -> Double.compare(r2.getScore(), r1.getScore())); // Tri décroissant

        this.bestModels = this.models.stream()
                .limit(5)
                .toList();
    }


    // Crée la prochaine génération de modèles en appliquant des mutations
    private void createNextGenerationFromList() {
        int numberOfMutants = this.simulationPerGeneration /5;
        int numberOfRandom = (int) Math.round(this.simulationPerGeneration *0.05); // 5% de la population

        this.models.clear();

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

    // Crée la prochaine génération de modèles en appliquant des mutations
    private void createNextGenerationFromOne(NeuralNetwork sourceModel) {
        System.out.println("Création de la première génération de cette session number of new sourceModel mutation : " + this.simulationPerGeneration);
        for (int i = 0; i < this.simulationPerGeneration; i++) {
            this.models.add(sourceModel.mutate(this.mutationAmplitude)); // Clone et applique une mutation
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
        return (int) Math.round(mutationAmplitude*100);
    }
}
