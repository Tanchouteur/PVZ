package fr.tanchou.pvz.ia.network;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    private List<Neuron> inputs; // Liste des neurones entrants
    private final List<Double> weights; // Liste des poids associés à chaque entrée
    private double output; // La sortie du neurone

    // Constructeur avec poids donnés
    public Neuron(List<Neuron> inputsNeuron, List<Double> weights) {
        if (inputsNeuron.size() != weights.size() && !inputsNeuron.isEmpty()) {
            throw new IllegalArgumentException("Le nombre de poids doit correspondre au nombre d'entrées : inputNeurone size " + inputsNeuron.size()+ ", weights size " + weights.size());
        }
        this.inputs = inputsNeuron;
        this.weights = weights;
    }

    // Constructeur poids aléatoires
    public Neuron(List<Neuron> inputsNeuron) {
        this.inputs = inputsNeuron;
        this.weights = new ArrayList<>();

        // Initialisation des poids aléatoirement
        for (int i = 0; i < inputs.size(); i++) {
            this.weights.add(Math.random()); // Poids initial aléatoire
        }
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
}

