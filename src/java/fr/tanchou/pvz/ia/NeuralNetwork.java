package fr.tanchou.pvz.ia;

public class NeuralNetwork {
    private final double[][] weightsInputHidden;
    private final double[] biasHidden;
    private final double[][] weightsHiddenOutput;
    private final double[] biasOutput;

    public NeuralNetwork(int inputSize, int hiddenSize, int outputSize) {
        weightsInputHidden = new double[inputSize][hiddenSize];
        biasHidden = new double[hiddenSize];
        weightsHiddenOutput = new double[hiddenSize][outputSize];
        biasOutput = new double[outputSize];

        // Initialisation aléatoire des poids
        initializeWeights(weightsInputHidden);
        initializeWeights(weightsHiddenOutput);
    }

    private void initializeWeights(double[][] weights) {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = Math.random() * 2 - 1; // Valeurs entre -1 et 1
            }
        }
    }

    public double[] predict(double[] inputs) {
        // Propagation avant : input -> hidden
        double[] hidden = new double[biasHidden.length];
        for (int i = 0; i < hidden.length; i++) {
            hidden[i] = biasHidden[i];
            for (int j = 0; j < inputs.length; j++) {
                hidden[i] += inputs[j] * weightsInputHidden[j][i];
            }
            hidden[i] = sigmoid(hidden[i]);
        }

        // Propagation avant : hidden -> output
        double[] output = new double[biasOutput.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = biasOutput[i];
            for (int j = 0; j < hidden.length; j++) {
                output[i] += hidden[j] * weightsHiddenOutput[j][i];
            }
            output[i] = sigmoid(output[i]);
        }
        return output;
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}