package fr.tanchou.pvz.ia.utils;

import fr.tanchou.pvz.ia.data.ModelManager;
import fr.tanchou.pvz.ia.network.NeuralNetwork;
import fr.tanchou.pvz.web.MultiOutputStream;
import org.java_websocket.WebSocket;

import java.util.*;

public class GenerationManager {
    private final PopulationManager populationManager;
    private final EvaluationManager evaluationManager;

    private int nbThreadToUse = 8;

    private int generationNumber = 0;
    private double mutationAmplitude = 1.0;

    private boolean continueTraining = true;

    // Constructeur
    public GenerationManager(NeuralNetwork sourceModel) {
        this.populationManager = new PopulationManager(sourceModel);
        this.evaluationManager = new EvaluationManager();
    }

    public void fullAutoTrain() {
        this.continueTraining = true;

        while (evaluationManager.needTraining() && this.continueTraining) {

            this.trainModel();

            if (this.generationNumber % 10 == 0) {
                ModelManager.saveModel(populationManager.getBestModelOverall(), "bestModelOverall");
            }
        }
    }

    public void semiAutoTrain(int nbGen) {
        this.continueTraining = true;
        for (int i = 0; i < nbGen; i++) {
            if (!this.continueTraining)
                break;

            this.trainModel();
            ModelManager.saveModel(populationManager.getBestModelOverall(), "bestModelOverall");
        }
    }

    // Méthode pour faire évoluer les modèles
    private void trainModel() {
        System.out.println("========= Génération " + this.generationNumber + " ==========");

        // Préparation de la génération suivante
        populationManager.prepareNextGeneration(this.mutationAmplitude);

        populationManager.evolve(evaluationManager.chooseRandomLevel(populationManager.getBestModels().get(1), generationNumber), this.nbThreadToUse);

        // Sélectionner les meilleurs modèles
        populationManager.selectBestModels();

        this.mutationAmplitude = evaluationManager.computeStatistics(populationManager.getBestModels(), this.generationNumber);

        populationManager.printBestModels();

        // Envoi des statistiques aux clients
        for (WebSocket ws : MultiOutputStream.webSocket) {
            ws.send(this.getStatistics());
        }

        System.out.println("========= Fin de la génération " + (this.generationNumber) + " =========");
        this.generationNumber++;
    }

    public void trainSandbox(boolean random) {

        populationManager.prepareNextGeneration(this.mutationAmplitude);

        populationManager.evolve(random, this.nbThreadToUse);

        populationManager.selectBestModels();

        evaluationManager.computeStatistics(populationManager.getBestModels(), this.generationNumber);

        populationManager.printBestModels();
    }

    public void setMutationAmplitude(double mutationAmplitude) {
        this.mutationAmplitude = mutationAmplitude;
    }

    public List<NeuralNetwork> getBestModels() {
        return populationManager.getBestModels();
    }

    public List<NeuralNetwork> getAllModels() {
        return populationManager.getAllModels();
    }

    public int getMutationAmplitude() {
        return (int) (mutationAmplitude * 100);
    }

    public void resetGenerationNumber() {
        this.generationNumber = 0;
    }

    public int getNumberOfThreads() {
        return this.nbThreadToUse;
    }

    public void setNumberOfThreads(int nbThreads) {
        this.nbThreadToUse = nbThreads;
    }

    public void stopTraining() {
        this.continueTraining = false;
        System.out.println("Training stopped.");
    }

    public void resumeTraining() {
        this.continueTraining = true;
    }

    public String getStatistics() {
        return "Statistics " + this.generationNumber + " " + evaluationManager.getStatistics(populationManager.getBestModelOverall().getScore());
    }

    public String getConfiguration() {
        return "Configuration " + this.getNumberOfThreads() + " " + this.populationManager.getSimulationPerGeneration() + " " + this.getMutationAmplitude();
    }

    public void updateConfiguration(int nbThreads, int simPerGen, double mutationAmp) {
        this.setNumberOfThreads(nbThreads);
        this.populationManager.setSimulationPerGeneration(simPerGen);
        this.setMutationAmplitude(mutationAmp);
    }

    public void setSimulationPerGeneration(int simulations) {
        this.populationManager.setSimulationPerGeneration(simulations);
    }

    public void createRandomGeneration() {
        this.populationManager.createRandomGeneration();
    }

    public void saveBestModel(String fileName) {
        ModelManager.saveModel(populationManager.getBestModelOverall(), fileName);
    }
}