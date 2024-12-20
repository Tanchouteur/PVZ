package fr.tanchou.pvz.ia.utils;

import fr.tanchou.pvz.ia.network.NeuralNetwork;
import fr.tanchou.pvz.web.MultiOutputStream;
import org.java_websocket.WebSocket;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PopulationManager {
    private final List<NeuralNetwork> models = new ArrayList<>();
    private final List<NeuralNetwork> bestModels = new ArrayList<>();
    private NeuralNetwork bestModelOverall;

    private final IAEnvironmentManager environmentManager;
    private final int[] networkLayers = new int[]{230, 256, 128, 64, 52};


    private int simulationPerGeneration = 5000;

    public PopulationManager(NeuralNetwork initialModel) {
        this.bestModelOverall = initialModel;
        this.environmentManager = new IAEnvironmentManager();
    }

    public void prepareNextGeneration(double mutationAmplitude) {
        if (bestModels.isEmpty()) {
            createNextGenerationFromOne(bestModelOverall, mutationAmplitude);
        } else {
            createNextGenerationFromList(mutationAmplitude);
        }
    }

    //méthode pour faire évoluer les modèles
    public void evolve(boolean random, int nbThreadToUse) {

        System.out.println("========= Evolution de la génération, avec niveau aléatoire: " + random + " =========");
        this.environmentManager.initializeGames(this.models, random, nbThreadToUse);

        // Attente de la fin de toutes les simulations
        while (!environmentManager.areAllSimulationsCompleted()) {
            System.out.println("Simulation advancement : " + environmentManager.getPrcntgOfSimulationsCompleted());
            for (WebSocket ws : MultiOutputStream.webSocket) {
                ws.send("Training " + environmentManager.getPrcntgOfSimulationsCompleted()+"%");
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("============ Fin d'évolution ============");
    }

    void selectBestModels() {

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

    // Crée la prochaine génération de modèles en appliquant des mutations
    public void createNextGenerationFromList(double mutationAmplitude) {
        int numberOfMutants = this.simulationPerGeneration / this.bestModels.size();  // Nombre de mutations par meilleur modèle
        int numberOfRandom = (int) Math.round(this.simulationPerGeneration * 0.05); // 5% de la population
        this.models.clear();

        // Appliquer des mutations aux meilleurs modèles
        for (int i = 0; i < numberOfMutants; i++) {
            for (NeuralNetwork model : this.bestModels) {
                this.models.add(model.mutate(mutationAmplitude));
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
    }

    // Crée la première génération de modèles en appliquant des mutations
    void createNextGenerationFromOne(NeuralNetwork sourceModel, double mutationAmplitude) {
        System.out.println("Création de la première génération de cette session : " + this.simulationPerGeneration + " mutations");
        for (int i = 0; i < this.simulationPerGeneration; i++) {
            this.models.add(sourceModel.mutate(mutationAmplitude));  // Clone et applique une mutation
        }
    }

    public void createRandomGeneration() {
        this.models.clear();
        for (int i = 0; i < this.simulationPerGeneration; i++) {
            this.models.add(new NeuralNetwork(new int[]{230, 256, 128, 64, 52}));
        }
    }

    void printBestModels() {
        // Affichage des meilleurs modèles
        System.out.println("\n=========== Best models ===========");
        for (NeuralNetwork model : this.bestModels) {
            System.out.println("Best model score: " + model.getScore());
        }
        System.out.println("===================================\n");

        System.out.println("Generation size " + this.models.size());
    }

    public NeuralNetwork getBestModelOverall() {
        return this.bestModelOverall;
    }

    public List<NeuralNetwork> getBestModels() {
        return bestModels;
    }

    public List<NeuralNetwork> getAllModels() {
        return this.models;
    }

    public int getSimulationPerGeneration() {
        return this.simulationPerGeneration;
    }

    public void setSimulationPerGeneration(int i) {
        this.simulationPerGeneration = i;
    }

}
