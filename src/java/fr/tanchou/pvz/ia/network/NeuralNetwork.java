package fr.tanchou.pvz.ia.network;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private final List<Neuron[]> layers;
    private int score = 0;

    public NeuralNetwork(int[] neuronsPerLayer) {
        layers = new ArrayList<>();

        // Créer les couches du réseau
        for (int i = 0; i < neuronsPerLayer.length; i++) {
            Neuron[] layer = new Neuron[neuronsPerLayer[i]];

            for (int j = 0; j < neuronsPerLayer[i]; j++) {

                // Crée des neurones dans chaque couche
                Neuron[] inputs = i == 0 ? new Neuron[neuronsPerLayer[i]] : layers.get(i - 1); // Entrées provenant de la couche précédente
                layer[j] = new Neuron(inputs); // Crée le neurone avec les entrées
            }
            layers.add(layer);
        }
    }

    public NeuralNetwork(List<Neuron[]> layers) {
        this.layers = layers;
    }

    // Méthode pour faire passer les entrées à travers le réseau
    public void feedForward(double[] inputs) {

        for (int i = 0; i < inputs.length; i++) {
            layers.getFirst()[i].setOutput(inputs[i]);
        }


        for (int i = 1; i < layers.size(); i++) {
            for (Neuron neuron : layers.get(i)) {
                neuron.calculateOutput();
            }
        }
    }

    public double[] getOutput() {

        Neuron[] outputLayer = layers.getLast();
        double[] outputs = new double[outputLayer.length];

        for (int i = 0; i < outputLayer.length; i++) {
            outputs[i] = outputLayer[i].getOutput(); // Récupère les sorties des neurones de la dernière couche
        }

        return outputs;
    }

    public NeuralNetwork mutate(double mutationAmplitude) {
        //System.out.println("Mutation amplitude : " + mutationAmplitude);
        NeuralNetwork mutatedNetwork = this.cloneNetwork();

        // Parcours chaque couche (sauf la couche d'entrée)
        for (int i = 1; i < mutatedNetwork.layers.size(); i++) {
            for (Neuron neuron : mutatedNetwork.layers.get(i)) {
                double[] weights = neuron.getWeights().clone(); // Créer une nouvelle liste pour les poids

                // Applique une mutation aléatoire à chaque poids
                for (int j = 0; j < weights.length; j++) {
                    double currentWeight = weights[j];

                    // Si la chance de mutation est remplie (10% ici).
                    if (Math.random() < 0.4) {
                        double mutation;
                        if (Math.random() < 0.8) {
                            // Mutation gaussienne (petite variation)
                            mutation = Math.random() * mutationAmplitude - mutationAmplitude / 2; // Variation contrôlée par l'amplitude
                        } else {
                            // Mutation radicale (plus rare)
                            mutation = (Math.random() * 2 - 1) * Math.abs(currentWeight) * mutationAmplitude; // Variation radicale contrôlée par l'amplitude
                        }

                        double newWeight = currentWeight + mutation;

                        // Limiter les poids entre -1 et 1.
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
                        weights[j] = newWeight; // Met à jour le poids après mutation
                    }
                }

                // Mise à jour des poids du neurone avec la nouvelle liste
                neuron.setWeights(weights);

                // Mutation du biais
                if (Math.random() < 0.1) { // Chance de mutation du biais
                    double currentBias = neuron.getBias(); // Obtenir le biais actuel
                    double biasMutation = Math.random() * mutationAmplitude - mutationAmplitude / 2; // Mutation du biais
                    double newBias = currentBias + biasMutation;

                    // Limiter le biais entre -1 et 1.
                    newBias = Math.max(-1, Math.min(1, newBias));
                    //System.out.println("Mutation du biais : " + currentBias + " -> " + newBias);
                    // Appliquer la mutation du biais
                    neuron.setBias(newBias); // Mettre à jour le biais
                }
            }
        }

        return mutatedNetwork;
    }

    public List<Neuron[]> getLayers() {
        return layers;
    }

    public NeuralNetwork cloneNetwork() {
        List<Neuron[]> clonedLayers = new ArrayList<>(this.layers.size());

        // Dupliquer chaque couche

        for (Neuron[] layer : this.layers) {
            Neuron[] clonedLayer = new Neuron[layer.length];
            int i = 0;
            for (Neuron neuron : layer) {
                // Copier les poids et les entrées
                double[] clonedWeights = neuron.getWeights().clone(); // Copie des poids
                Neuron[] clonedInputs = neuron.getInputs().clone(); // Copie des entrées (neurones de la couche précédente)
                double bias = neuron.getBias(); // Copie du biais
                clonedLayer[i] = (new Neuron(clonedInputs, clonedWeights, bias)); // Crée le neurone cloné avec les poids et entrées
                i++;
            }
            clonedLayers.add(clonedLayer);

        }

        // Relier les entrées des neurones (à partir de la 2ᵉ couche).
        for (int i = 1; i < clonedLayers.size(); i++) { // Commence à la 2e couche
            Neuron[] prevLayer = clonedLayers.get(i - 1);
            for (Neuron neuron : clonedLayers.get(i)) {
                neuron.setInputs(prevLayer); // Relie les entrées
            }
        }

        return new NeuralNetwork(clonedLayers);
    }

    public double[] getWeights() {
        // Calcule le nombre total de poids dans toutes les couches, sauf la couche d'entrée
        int totalWeights = 0;
        for (int i = 1; i < layers.size(); i++) {
            for (Neuron neuron : layers.get(i)) {
                totalWeights += neuron.getWeights().length; // Nombre de poids dans chaque neurone
            }
        }

        // Crée un tableau pour contenir tous les poids
        double[] weightsArray = new double[totalWeights];
        int index = 0;

        // Remplissage du tableau avec les poids de chaque neurone
        for (int i = 1; i < layers.size(); i++) {
            for (Neuron neuron : layers.get(i)) {
                for (Double weight : neuron.getWeights()) {
                    weightsArray[index++] = weight; // Ajoute chaque poids dans le tableau
                }
            }
        }

        return weightsArray; // Retourne le tableau de poids
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}