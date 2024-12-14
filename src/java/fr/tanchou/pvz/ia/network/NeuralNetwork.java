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
        //System.out.println("feedForward");

        if (inputs.length != layers.getFirst().length) {
            throw new IllegalArgumentException("Le nombre d'entrées ne correspond pas au nombre de neurones de la couche d'entrée");
        }

        // Étape 1 : Initialiser les sorties des neurones de la couche d'entrée
        for (int i = 0; i < inputs.length; i++) {
            layers.getFirst()[i].setOutput(inputs[i]);
        }

        // Étape 2 : Propagation dans les couches cachées
        for (int i = 1; i < layers.size(); i++) {
            boolean isLastLayer = (i == layers.size() - 1);
            Neuron[] currentLayer = layers.get(i);
            Neuron[] previousLayer = layers.get(i - 1);

            // Calcul des sorties pour la couche actuelle
            double[] layerOutputs = new double[currentLayer.length];
            for (int j = 0; j < currentLayer.length; j++) {
                layerOutputs[j] = this.calculateNeuronOutput(currentLayer[j], previousLayer);
            }

            // Appliquer l'activation (Softmax uniquement sur la dernière couche)
            if (isLastLayer) {
                layerOutputs = this.softmax(layerOutputs);
            } else {
                for (int j = 0; j < layerOutputs.length; j++) {
                    layerOutputs[j] = this.relu(layerOutputs[j]);
                }
            }

            // Stocker les sorties dans les neurones de la couche actuelle
            for (int j = 0; j < currentLayer.length; j++) {
                currentLayer[j].setOutput(layerOutputs[j]);
            }
        }
        //System.out.println("feedForward done");
    }

    // Méthode pour calculer la somme pondérée (avant activation) d'un neurone
    private double calculateNeuronOutput(Neuron neuron, Neuron[] previousLayer) {
        double sum = 0;
        for (int i = 0; i < previousLayer.length; i++) {
            sum += previousLayer[i].getOutput() * neuron.weights[i];
        }
        sum += neuron.bias; // Ajout du biais
        return sum;
    }

    // Fonction d'activation Softmax (appliquée sur une couche entière)
    public double[] softmax(double[] inputs) {
        double max = Double.NEGATIVE_INFINITY;

        // Étape 1 : Trouver le maximum pour éviter les dépassements (overflow)
        for (double input : inputs) {
            if (input > max) max = input;
        }

        // Étape 2 : Calculer les exponentielles en normalisant
        double sum = 0;
        double[] expValues = new double[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            expValues[i] = Math.exp(inputs[i] - max); // Soustraire max pour la stabilité numérique
            sum += expValues[i];
        }

        // Étape 3 : Normaliser pour obtenir des probabilités
        for (int i = 0; i < expValues.length; i++) {
            expValues[i] /= sum;
        }

        return expValues;
    }

    // Fonction d'activation ReLU
    public double relu(double x) {
        return Math.max(0, x);
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