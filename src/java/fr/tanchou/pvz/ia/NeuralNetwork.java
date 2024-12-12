package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.ia.network.Neuron;

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

    // Méthode pour faire passer les entrées à travers le réseau
    public void feedForward(double[] inputs) {

        // Remplir la couche d'entrée avec les données
        for (int i = 0; i < inputs.length; i++) {
            layers.get(0).get(i).setValue(inputs[i]);
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
}