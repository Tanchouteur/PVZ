package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.ia.network.NeuralNetwork;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GenerationManager {
    private List<NeuralNetwork> models;  // Liste des modèles de réseaux neuronaux
    private final IAEnvironmentManager environmentManager;  // Gère les simulations d'IA

    public GenerationManager(List<NeuralNetwork> initialModels, int numberOfGames) {
        this.models = initialModels;
        this.environmentManager = new IAEnvironmentManager(numberOfGames);
    }

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

        // Appliquer des mutations et créer la prochaine génération
        List<NeuralNetwork> nextGeneration = createNextGeneration(bestModels);

        // Remplacer la génération actuelle avec la suivante
        this.models = nextGeneration;
    }

    // Sélectionne les meilleurs modèles en fonction de leurs performances
    private List<NeuralNetwork> selectBestModels(List<IAGameResult> results) {
        results.sort(Comparator.comparingDouble(IAGameResult::calculateScore).reversed());

        List<NeuralNetwork> bestModels = new ArrayList<>();
        for (int i = 0; i < results.size() / 3; i++) {  // Par exemple, on garde les 50% meilleurs
            bestModels.add(this.models.get(i));  // Associer les résultats aux modèles
        }
        return bestModels;
    }

    // Crée la prochaine génération de modèles en appliquant des mutations
    private List<NeuralNetwork> createNextGeneration(List<NeuralNetwork> bestModels) {
        List<NeuralNetwork> nextGeneration = new ArrayList<>();

        for (NeuralNetwork model : bestModels) {
            nextGeneration.add(model.mutate());  // Appliquer une mutation
        }

        return nextGeneration;
    }

    public List<NeuralNetwork> getModels() {
        return this.models;
    }

    public IAEnvironmentManager getEnvironmentManager() {
        return this.environmentManager;
    }
}
