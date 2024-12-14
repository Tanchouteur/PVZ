package fr.tanchou.pvz.ia.network;

public class Neuron {
    private Neuron[] inputs; // Liste des neurones entrants
    protected double[] weights; // Liste des poids associés à chaque entrée
    protected double bias; // Biais du neurone
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
