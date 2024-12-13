package fr.tanchou.pvz.ia.network;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    private List<Neuron> inputs; // Liste des neurones entrants
    private final List<Double> weights; // Liste des poids associés à chaque entrée
    private double bias; // Biais du neurone
    private double output; // La sortie du neurone

    // Constructeur avec poids donnés et biais
    public Neuron(List<Neuron> inputsNeuron, List<Double> weights, double bias) {
        if (inputsNeuron.size() != weights.size() && !inputsNeuron.isEmpty()) {
            throw new IllegalArgumentException("Le nombre de poids doit correspondre au nombre d'entrées : inputNeurone size " + inputsNeuron.size()+ ", weights size " + weights.size());
        }
        this.inputs = inputsNeuron;
        this.weights = weights;
        this.bias = bias;
    }

    // Constructeur avec poids et biais aléatoires
    public Neuron(List<Neuron> inputsNeuron) {
        this.inputs = inputsNeuron;
        this.weights = new ArrayList<>();

        // Initialisation des poids aléatoires
        for (int i = 0; i < inputs.size(); i++) {
            weights.add(Math.random() * 2 - 1); // Poids entre -1 et 1.
        }

        // Initialisation du biais aléatoire
        this.bias = Math.random() * 2 - 1; // Biais entre -1 et 1.
    }

    // Fonction d'activation sigmoïde
    public double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    // Calculer la sortie du neurone
    public void calculateOutput() {
        double sum = 0;
        for (int i = 0; i < inputs.size(); i++) {
            sum += inputs.get(i).getOutput() * weights.get(i); // Somme pondérée des entrées
        }
        sum += bias; // Ajout du biais
        this.output = sigmoid(sum); // Applique la fonction d'activation
    }

    // Getter et setter
    public double getOutput() {
        return this.output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public List<Double> getWeights() {
        return this.weights;
    }

    public void setInputs(List<Neuron> prevLayer) {
        this.inputs = prevLayer;
    }

    public void setWeights(List<Double> weights) {
        if (weights.size() != this.inputs.size()) {
            throw new IllegalArgumentException("Le nombre de poids doit correspondre au nombre d'entrées : inputNeurone size " + inputs.size()+ ", weights size " + weights.size());
        }
        this.weights.clear();
        this.weights.addAll(weights);
    }

    public double getBias() {
        return this.bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public List<Neuron> getInputs() {
        return this.inputs;
    }
}
