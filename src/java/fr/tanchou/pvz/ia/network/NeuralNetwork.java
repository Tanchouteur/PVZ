package fr.tanchou.pvz.ia.network;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    private final List<Neuron[]> layers;
    private int score = 0;

    // Constructeur pour créer un réseau de neurones à partir de couches données
    public NeuralNetwork(List<Neuron[]> layers) {
        this.layers = layers;
    }

    // Constructeur pour créer un réseau de neurones aléatoire
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

    // Méthode pour faire passer les entrées à travers le réseau
    public void feedForward(double[] inputs) {

        for (int i = 0; i < inputs.length; i++) {
            layers.getFirst()[i].setOutput(inputs[i]);
        }


        for (int i = 1; i < layers.size(); i++) {
            boolean isHiddenLayer = i != layers.size() - 1;
            for (Neuron neuron : layers.get(i)) {
                neuron.calculateOutput(isHiddenLayer);
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
        NeuralNetwork mutatedNetwork = this.cloneNetwork();
        //System.out.println("Mutation avec une amplitude de " + mutationAmplitude);
        // Parcours chaque couche (sauf la couche d'entrée)
        for (int i = 1; i < mutatedNetwork.layers.size(); i++) {
            for (Neuron neuron : mutatedNetwork.layers.get(i)) {
                double[] weights = neuron.getWeights().clone();

                // Mutation des poids
                for (int j = 0; j < weights.length; j++) {
                    if (Math.random() < 0.5) { // Probabilité de mutation
                        // Type de mutation : petite variation ou variation proportionnelle au poids actuel
                        double mutation = (Math.random() < 0.8)
                                ? (Math.random() - 0.5) * mutationAmplitude // Petite variation
                                : (Math.random() * 2 - 1) * Math.abs(weights[j]) * mutationAmplitude; // Variation proportionnelle

                        double newWeight = weights[j] + mutation;

                        // Contraindre le poids dans la plage [-1, 1]
                        newWeight = Math.max(-1, Math.min(1, newWeight));

                        // Ajout d'une probabilité de conserver le poids initial (stabilité)
                        if (Math.random() < 0.2) {
                            newWeight = weights[j];
                        }

                        //System.out.println("Mutation du poids : " + weights[j] + " -> " + newWeight);
                        weights[j] = newWeight;
                    }
                }
                neuron.setWeights(weights);

                // Mutation du biais
                if (Math.random() < 0.2) { // Augmenter la probabilité de mutation des biais
                    double currentBias = neuron.getBias();

                    // Mutation basée sur l'activation et une variation aléatoire
                    double newBias = currentBias + neuron.getOutput() * mutationAmplitude * (Math.random() - 0.5);

                    // Contraindre le biais dans la plage [-1, 1]
                    newBias = Math.max(-1, Math.min(1, newBias));

                    //System.out.println("Mutation du biais : " + currentBias + " -> " + newBias);

                    neuron.setBias(newBias);
                }
            }
        }

        return mutatedNetwork;
    }


    public NeuralNetwork mutateOld(double mutationAmplitude) {
        NeuralNetwork mutatedNetwork = this.cloneNetwork();

        // Parcours chaque couche (sauf la couche d'entrée)
        for (int i = 1; i < mutatedNetwork.layers.size(); i++) {
            for (Neuron neuron : mutatedNetwork.layers.get(i)) {
                double[] weights = neuron.getWeights().clone();

                // Mutation des poids
                for (int j = 0; j < weights.length; j++) {
                    if (Math.random() < 0.4) { // Probabilité de mutation
                        double mutation = (Math.random() < 0.8)
                                ? Math.random() * mutationAmplitude - mutationAmplitude / 2 // Petite variation
                                : (Math.random() * 2 - 1) * Math.abs(weights[j]) * mutationAmplitude; // Variation radicale

                        double newWeight = weights[j] + mutation;
                        newWeight = Math.max(-1, Math.min(1, newWeight)); // Contraindre entre -1 et 1

                        // Appliquer des pénalités si nécessaire
                        if (Math.abs(newWeight) > 0.9) {
                            newWeight = Math.signum(newWeight) * 0.9;
                        } else if (Math.abs(newWeight) < 0.05) {
                            newWeight = 0.05 * Math.signum(newWeight);
                        }

                        if (Math.random() < 0.2) {
                            newWeight = weights[j]; // Conserver certains poids
                        }
                        System.out.println("Mutation du poids : " + weights[j] + " -> " + newWeight);
                        weights[j] = newWeight;
                    }
                }
                neuron.setWeights(weights);

                // Mutation du biais
                if (Math.random() < 0.1) {
                    double currentBias = neuron.getBias();
                    double newBias = currentBias + neuron.getOutput() * mutationAmplitude * (Math.random() - 0.5);

                    System.out.println("Mutation du biais : " + currentBias + " -> " + newBias);

                    neuron.setBias(Math.max(-1, Math.min(1, newBias)));
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}