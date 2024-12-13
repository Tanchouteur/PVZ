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
            layers.get(0).get(i).setOutput(inputs[i]); // Accéder à la première couche avec get(0)
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
                List<Double> weights = new ArrayList<>(neuron.getWeights()); // Créer une nouvelle liste pour les poids

                // Applique une mutation aléatoire à chaque poids
                for (int j = 0; j < weights.size(); j++) {
                    double currentWeight = weights.get(j);

                    // Si la chance de mutation est remplie (10% ici)
                    if (Math.random() < 0.1) {
                        double mutation;
                        if (Math.random() < 0.8) {
                            // Mutation gaussienne (petite variation)
                            mutation = Math.random() * 0.2 - 0.1; // Variation entre -0.1 et 0.1
                        } else {
                            // Mutation radicale (plus rare)
                            mutation = (Math.random() * 2 - 1) * Math.abs(currentWeight); // Variation radicale réduite par la valeur actuelle du poids
                        }

                        double newWeight = currentWeight + mutation;

                        // Limiter les poids entre -1 et 1
                        newWeight = Math.max(-1, Math.min(1, newWeight));

                        // Pénalité si le poids devient trop extrême
                        if (Math.abs(newWeight) > 0.9) {
                            newWeight = Math.signum(newWeight) * 0.9; // Rapproche les poids extrêmes de 0.9 ou -0.9
                        }

                        // Optionnel : conserver certains poids avec une probabilité
                        if (Math.random() < 0.2) {
                            newWeight = currentWeight; // Pas de mutation
                        }

                        // Applique la pénalité si le poids devient trop faible
                        if (Math.abs(newWeight) < 0.05) {
                            newWeight = 0.05 * Math.signum(newWeight); // Pénaliser les poids trop faibles
                        }

                        weights.set(j, newWeight); // Met à jour le poids après mutation
                    }
                }

                // Mise à jour des poids du neurone avec la nouvelle liste
                neuron.setWeights(weights);
            }
        }

        return mutatedNetwork;
    }



    public List<List<Neuron>> getLayers() {
        return layers;
    }

    public NeuralNetwork cloneNetwork() {
        List<List<Neuron>> clonedLayers = new ArrayList<>();
        int l = 0;

        // Dupliquer chaque couche
        for (List<Neuron> layer : this.layers) {
            List<Neuron> clonedLayer = new ArrayList<>();
            for (Neuron neuron : layer) {
                // Copier les poids et les entrées
                List<Double> clonedWeights = new ArrayList<>(neuron.getWeights()); // Copie des poids
                List<Neuron> clonedInputs = new ArrayList<>(neuron.getInputs()); // Copie des entrées (neurones de la couche précédente)
                clonedLayer.add(new Neuron(clonedInputs, clonedWeights, l)); // Crée le neurone cloné avec les poids et entrées
            }
            clonedLayers.add(clonedLayer);
            l++;
        }

        // Relier les entrées des neurones (à partir de la 2ème couche)
        for (int i = 1; i < clonedLayers.size(); i++) { // Commence à la 2e couche
            List<Neuron> prevLayer = clonedLayers.get(i - 1);
            for (Neuron neuron : clonedLayers.get(i)) {
                neuron.setInputs(prevLayer); // Relie les entrées
            }
        }

        return new NeuralNetwork(clonedLayers);
    }


}