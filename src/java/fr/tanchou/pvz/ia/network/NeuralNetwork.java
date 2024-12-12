package fr.tanchou.pvz.ia.network;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private final List<List<Neuron>> layers; // Liste des couches (chaque couche est une liste de neurones)

    public NeuralNetwork(int[] neuronsPerLayer) {
        layers = new ArrayList<>();

        // Créer les couches du réseau
        for (int i = 0; i < neuronsPerLayer.length; i++) {
            List<Neuron> layer = new ArrayList<>();

            for (int j = 0; j < neuronsPerLayer[i]; j++) {

                // Crée des neurones dans chaque couche
                List<Neuron> inputs = i == 0 ? new ArrayList<>() : layers.get(i - 1); // Entrées provenant de la couche précédente
                layer.add(new Neuron(inputs)); // Crée le neurone
            }
            layers.add(layer);
        }
    }

    public NeuralNetwork(List<List<Neuron>> layers) {
        this.layers = layers;
    }

    // Méthode pour faire passer les entrées à travers le réseau
    public void feedForward(double[] inputs) {

        // Remplir la couche d'entrée avec les données
        for (int i = 0; i < inputs.length; i++) {
            layers.getFirst().get(i).setOutput(inputs[i]);
        }

        // Calculer les sorties pour chaque neurone (couche par couche)
        for (int i = 1; i < layers.size(); i++) {
            for (Neuron neuron : layers.get(i)) {
                neuron.calculateOutput(); // Calcule la sortie du neurone
            }
        }
    }

    // Getter pour obtenir la sortie finale
    public double[] getOutput() {

        List<Neuron> outputLayer = layers.getLast(); // Récupère la dernière couche
        double[] outputs = new double[outputLayer.size()];

        for (int i = 0; i < outputLayer.size(); i++) {
            outputs[i] = outputLayer.get(i).getOutput(); // Récupère les sorties des neurones de la dernière couche
        }

        return outputs;
    }

    public NeuralNetwork mutate() {
        NeuralNetwork mutatedNetwork = this.cloneNetwork(); // Clone le réseau

        // Parcours chaque couche (sauf la couche d'entrée)
        for (int i = 1; i < mutatedNetwork.layers.size(); i++) {
            for (Neuron neuron : mutatedNetwork.layers.get(i)) {
                List<Double> weights = neuron.getWeights();

                // Applique une mutation aléatoire à chaque poids
                for (int j = 0; j < weights.size(); j++) {
                    if (Math.random() < 0.1) { // 10% de chance de mutation
                        double mutation = (Math.random() * 2 - 1) * 0.5; // Variation entre -0.5 et 0.5
                        //avant mutation
                        //System.out.println("avant mutation : " + weights.get(j));
                        weights.set(j, weights.get(j) + mutation); // Modifie le poids
                        //après mutation
                        //System.out.println("après mutation : " + weights.get(j));
                    }
                }
            }
        }

        return mutatedNetwork;
    }

    public List<List<Neuron>> getLayers() {
        return layers;
    }

    public NeuralNetwork cloneNetwork() {
        List<List<Neuron>> clonedLayers = new ArrayList<>();

        // Dupliquer chaque couche
        for (List<Neuron> layer : this.layers) {
            List<Neuron> clonedLayer = new ArrayList<>();
            for (Neuron neuron : layer) {
                List<Double> clonedWeights = new ArrayList<>(neuron.getWeights()); // Copie des poids
                clonedLayer.add(new Neuron(new ArrayList<>(), clonedWeights)); // Entrées vides pour l'instant
            }
            clonedLayers.add(clonedLayer);
        }

        // Relier les entrées des neurones
        for (int i = 1; i < clonedLayers.size(); i++) { // Commence à la 2e couche

            List<Neuron> prevLayer = clonedLayers.get(i - 1);

            for (Neuron neuron : clonedLayers.get(i)) {
                neuron.setInputs(prevLayer); // Relie les entrées
            }
        }

        return new NeuralNetwork(clonedLayers);
    }

}