package fr.tanchou.pvz.ia.network;

public class Neuron {
    private Neuron[] inputs; // Liste des neurones entrants
    private double[] weights; // Liste des poids associés à chaque entrée
    private double bias; // Biais du neurone
    private double output; // La sortie du neurone

    // Constructeur avec poids donnés et biais
    public Neuron(Neuron[] inputsNeuron, double[] weights, double bias) {
        if (inputsNeuron.length != weights.length && !(inputsNeuron.length == 0)) {
            throw new IllegalArgumentException("Le nombre de poids doit correspondre au nombre d'entrées : inputNeurone size " + inputsNeuron.length+ ", weights size " + weights.length);
        }
        this.inputs = inputsNeuron;
        this.weights = weights;
        this.bias = bias;
    }

    // Constructeur avec poids et biais aléatoires
    public Neuron(Neuron[] inputsNeuron) {
        this.inputs = inputsNeuron;
        this.weights = new double[inputsNeuron.length];

        // Initialisation des poids aléatoires
        for (int i = 0; i < inputsNeuron.length; i++) {
            weights[i] = (Math.random() * 2 - 1); // Poids entre -1 et 1.
        }

        // Initialisation du biais aléatoire
        this.bias = Math.random() * 2 - 1; // Biais entre -1 et 1.
    }

    // Fonction d'activation sigmoïde
    public double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    // Fonction d'activation ReLU
    public double relu(double x) {
        return Math.max(0, x);
    }

    // Optimisation : éviter d'appeler getOutput() à chaque itération
    public void calculateOutput(boolean isHiddenLayer) {
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            double inputOutput = inputs[i].getOutput(); // Récupérer une fois la sortie du neurone
            sum += inputOutput * weights[i]; // Somme pondérée des entrées
        }
        sum += this.bias; // Ajout du biais
        if (isHiddenLayer) this.output = relu(sum); // Applique la fonction d'activation
        else {
            this.output = sigmoid(sum); // Applique la fonction d'activation
        }
    }


    // Getter et setter
    public double getOutput() {
        return this.output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double[] getWeights() {
        return this.weights;
    }

    public void setInputs(Neuron[] prevLayer) {
        this.inputs = prevLayer;
    }

    public void setWeights(double[] weights) {
        if (weights.length != this.inputs.length) {
            throw new IllegalArgumentException("Le nombre de poids doit correspondre au nombre d'entrées : inputNeurone size " + inputs.length+ ", weights size " + weights.length);
        }
        this.weights = weights;
    }

    public double getBias() {
        return this.bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public Neuron[] getInputs() {
        return this.inputs;
    }
}
