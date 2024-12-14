package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.ia.network.NeuralNetwork;
import fr.tanchou.pvz.ia.network.Neuron;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class ModelSaver {

    public static void saveModel(NeuralNetwork network, String filePath) {

        System.out.println("Saving "+ filePath +"...");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Structure des couches, des poids et des biais
        List<List<Map<String, Object>>> layersData = new ArrayList<>();
        for (Neuron[] layer : network.getLayers()) {
            List<Map<String, Object>> layerData = getMapList(List.of(layer));
            layersData.add(layerData);
        }

        // Convertir en JSON
        String json = gson.toJson(layersData);

        // Écrire dans un fichier
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
            System.out.println("Modèle sauvegardé avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier du modèle : " + e.getMessage());
        }
    }

    private static List<Map<String, Object>> getMapList(List<Neuron> layer) {
        List<Map<String, Object>> layerData = new ArrayList<>();
        for (Neuron neuron : layer) {
            Map<String, Object> neuronData = new HashMap<>();

            // Vérifier si les poids sont NaN avant de les ajouter
            List<Double> sanitizedWeights = new ArrayList<>();
            for (Double weight : neuron.getWeights()) {
                sanitizedWeights.add(Double.isNaN(weight) ? 0.0 : weight);
            }

            // Vérifier si le biais est NaN avant de l'ajouter
            double sanitizedBias = Double.isNaN(neuron.getBias()) ? 0.0 : neuron.getBias();

            // Vérifier si la sortie est NaN avant de l'ajouter
            double sanitizedOutput = Double.isNaN(neuron.getOutput()) ? 0.0 : neuron.getOutput();

            neuronData.put("weights", sanitizedWeights);
            neuronData.put("bias", sanitizedBias);
            neuronData.put("output", sanitizedOutput);
            layerData.add(neuronData);
        }
        return layerData;
    }

    // Méthode pour charger un modèle
    public static NeuralNetwork loadModel(String filePath) {
        System.out.println("Loading "+ filePath +"...");
        Gson gson = new Gson();

        // Lire le fichier JSON
        try (Reader reader = new FileReader(filePath)) {
            // Convertir le JSON en structure de données Java
            List<List<Map<String, Object>>> layersData = gson.fromJson(reader, List.class);

            List<Neuron[]> layers = new ArrayList<>();

            // Reconstruire le réseau
            for (int i = 0; i < layersData.size(); i++) {

                List<Map<String, Object>> layerData = layersData.get(i);
                Neuron[] layer = new Neuron[layerData.size()];
                int j = 0;
                for (Map<String, Object> neuronData : layerData) {

                    double[] weights = (double[]) neuronData.get("weights");// soucis ici
                    double bias = ((Number) neuronData.get("bias")).doubleValue(); // Charger le biais
                    double output = ((Number) neuronData.get("output")).doubleValue();

                    // Récupérer les entrées (neurones de la couche précédente)
                    Neuron[] inputs = i == 0 ? new Neuron[0] : layers.get(i - 1);
                    Neuron neuron = new Neuron(inputs, weights, bias); // Inclure le biais

                    neuron.setOutput(output);
                    layer[j] = neuron;
                    j++;
                }
                layers.add(layer);
            }

            System.out.println("Modèle chargé avec succès. Couches : " + layers.size());
            return new NeuralNetwork(layers);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier du modèle : " + e.getMessage());
            return null;
        }
    }
}