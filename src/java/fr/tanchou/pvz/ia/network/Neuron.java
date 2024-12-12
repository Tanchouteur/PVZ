package fr.tanchou.pvz.ia.network;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    private double output; // La sortie du neurone
    private final List<Neuron> inputs; // Liste des neurones entrants
    private final List<Double> weights; // Liste des poids associés à chaque entrée

    // Constructeur
    public Neuron(List<Neuron> inputs, List<Double> weights) {
        this.inputs = inputs;
        this.weights = weights;
    }

    // Constructeur
    public Neuron(List<Neuron> inputs) {
        this.inputs = inputs;
        this.weights = new ArrayList<>();

        // Initialise les poids aléatoirement
        for (int i = 0; i < inputs.size(); i++) {
            this.weights.add(Math.random() * 2 - 1); // Poids aléatoires entre -1 et 1
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
}
