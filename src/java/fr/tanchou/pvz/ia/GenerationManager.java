package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.ia.network.NeuralNetwork;

import java.util.*;

public class GenerationManager {
    private List<NeuralNetwork> models;  // Liste des modèles de réseaux neuronaux
    private IAEnvironmentManager environmentManager;  // Gère les simulations d'IA

    private int generationNumber = 2000;

    public GenerationManager(boolean loadBestModel) {
        NeuralNetwork bestModelLoaded;
        if (!loadBestModel) {

            bestModelLoaded = createInitialGeneration();  // Créer une génération initiale
            ModelSaver.saveModel(bestModelLoaded, "best_model.json");
        }else {
            bestModelLoaded = ModelSaver.loadModel("best_model.json");
        }

        this.models = createNextGenerationFromOne(bestModelLoaded);

        this.environmentManager = new IAEnvironmentManager();
    }

    // Méthode pour créer une génération initiale
    public NeuralNetwork createInitialGeneration() {
        // Crée un modèle avec 3 couches
        return new NeuralNetwork(new int[]{270, 100, 52});
    }

    // Méthode pour faire évoluer les modèles
    public void evolve() {
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


        List<IAGameResult> results = this.environmentManager.collectResults();

        // Sélectionner les meilleurs modèles
        List<NeuralNetwork> bestModels = selectBestModels(results);
        System.out.println("Best model score: " + results.get(0).getScore() + " \n Best model score: " + results.get(1).getScore() + " \n Best model score: " + results.get(2).getScore() + " \n Best model score: " + results.get(3).getScore() + " \n Best model score: " + results.get(4).getScore());

        ModelSaver.saveModel(bestModels.get(0), "best_model.json");

        // Remplacer la génération actuelle avec la suivante
        this.models = createNextGenerationFromList(bestModels);
        System.out.println("Generation size " + models.size());
    }

    private List<NeuralNetwork> selectBestModels(List<IAGameResult> results) {
        System.out.println("Size of results list before sorting: " + results.size());

        results.sort((r1, r2) -> Double.compare(r2.getScore(), r1.getScore())); // Tri décroissant

        List<IAGameResult> topResults = results.stream()
                .limit(5)
                .toList();


        return topResults.stream()
                .map(IAGameResult::getNeuralNetwork)
                .toList();
    }






    // Crée la prochaine génération de modèles en appliquant des mutations
    private List<NeuralNetwork> createNextGenerationFromList(List<NeuralNetwork> bestModels) {
        List<NeuralNetwork> nextGeneration = new ArrayList<>();
        int numberOfMutants = generationNumber/5;
        int numberOfRandom = (int) Math.round(generationNumber*0.05); // 5% de la population

        for (int i = 0; i < numberOfMutants; i++) {
            for (NeuralNetwork model : bestModels) {
                nextGeneration.add(model.mutate());
            }
        }

        // Ajout de modèles aléatoires
        for (int i = 0; i < numberOfRandom; i++) {
            nextGeneration.add(new NeuralNetwork(new int[]{270, 100, 52}));
        }

        return nextGeneration;
    }

    // Crée la prochaine génération de modèles en appliquant des mutations
    private List<NeuralNetwork> createNextGenerationFromOne(NeuralNetwork bestModel) {
        List<NeuralNetwork> nextGeneration = new ArrayList<>();

        // Nombre de mutants à générer
        int numberOfMutants = generationNumber;

        for (int i = 0; i < numberOfMutants; i++) {
            nextGeneration.add(bestModel.mutate()); // Clone et applique une mutation
        }

        return nextGeneration;
    }
}
