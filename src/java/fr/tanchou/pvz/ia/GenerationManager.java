package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.ia.network.NeuralNetwork;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GenerationManager {
    private List<NeuralNetwork> models;  // Liste des modèles de réseaux neuronaux
    private final IAEnvironmentManager environmentManager;  // Gère les simulations d'IA

    public GenerationManager(boolean loadBestModel) {
        NeuralNetwork bestModelLoaded;
        if (!loadBestModel) {
            bestModelLoaded = createInitialGeneration();  // Créer une génération initiale
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
        this.environmentManager.initializeGames(this.models);  // Lancer les simulations avec les modèles actuels
        this.environmentManager.startSimulations();

        while (!environmentManager.areAllSimulationsCompleted()) {
            try {
                Thread.sleep(1000); // Vérifie périodiquement si toutes les simulations sont terminées
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        this.environmentManager.stopSimulations();  // Arrêter les simulations

        // Collecter les résultats des jeux
        List<IAGameResult> results = this.environmentManager.collectResults();

        // Sélectionner les meilleurs modèles
        List<NeuralNetwork> bestModels = selectBestModels(results);

        ModelSaver.saveModel(bestModels.getFirst(), "best_model.json");

        // Remplacer la génération actuelle avec la suivante
        this.models = createNextGenerationFromList(bestModels);
    }

    // Sélectionne les meilleurs modèles en fonction de leurs performances
    private List<NeuralNetwork> selectBestModels(List<IAGameResult> results) {
        results.sort(Comparator.comparingDouble(IAGameResult::calculateScore).reversed());

        for (int i = 0; i < results.size()/2; i++) {
            System.out.println("Score du modèle " + i + " : " + results.get(i).calculateScore());
        }

        List<NeuralNetwork> bestModels = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            bestModels.add(this.models.get(i));     // Associer les résultats aux modèles
        }

        System.out.println("Modèle sélectionné : " + results.getFirst().calculateScore());
        return bestModels;
    }

    // Crée la prochaine génération de modèles en appliquant des mutations
    private List<NeuralNetwork> createNextGenerationFromList(List<NeuralNetwork> bestModels) {
        List<NeuralNetwork> nextGeneration = new ArrayList<>();

        for (NeuralNetwork model : bestModels) {
            nextGeneration.add(model.mutate());  // Appliquer une mutation
        }

        return nextGeneration;
    }

    // Crée la prochaine génération de modèles en appliquant des mutations
    private List<NeuralNetwork> createNextGenerationFromOne(NeuralNetwork bestModel) {
        List<NeuralNetwork> nextGeneration = new ArrayList<>();

        // Nombre de mutants à générer
        int numberOfMutants = 100;

        for (int i = 0; i < numberOfMutants; i++) {
            nextGeneration.add(bestModel.mutate()); // Clone et applique une mutation
        }

        return nextGeneration;
    }
}
