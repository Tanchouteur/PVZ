package fr.tanchou.pvz.ia.data;

import fr.tanchou.pvz.ia.network.NeuralNetwork;
import fr.tanchou.pvz.ia.network.Neuron;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class ModelSaver {

    public static String modelDirectory = "Models/"; // Répertoire de sauvegarde des modèles

    // Méthode pour sauvegarder un modèle
    public static void saveModel(NeuralNetwork network, String filePath) {
        filePath = modelDirectory + filePath + ".json";
        System.out.println("Saving in " + filePath + " - NetworkScore : " + network.getScore());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Structure des couches, des poids et des biais
        List<List<Map<String, Object>>> layersData = new ArrayList<>();
        for (Neuron[] layer : network.getLayers()) {
            List<Map<String, Object>> layerData = getMapList(Arrays.asList(layer)); // Conversion en liste
            layersData.add(layerData);
        }

        // Inclure le score dans les données JSON
        Map<String, Object> data = new HashMap<>();
        data.put("score", network.getScore());
        data.put("layers", layersData);

        // Convertir en JSON
        String json = gson.toJson(data);

        // Écrire dans un fichier
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
            System.out.println("Modèle sauvegardé avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier du modèle : " + e.getMessage());
        }
    }

    // Méthode pour charger un modèle
    public static NeuralNetwork loadModel(String filePath) {
        filePath = modelDirectory + filePath + ".json";
        System.out.println("Loading " + filePath + "...");
        Gson gson = new Gson();

        // Lire le fichier JSON
        try (Reader reader = new FileReader(filePath)) {
            // Convertir le JSON en structure de données Java
            Map<String, Object> data = gson.fromJson(reader, Map.class);

            // Charger le score
            double score = ((Number) data.get("score")).doubleValue();

            // Charger les couches
            List<List<Map<String, Object>>> layersData = (List<List<Map<String, Object>>>) data.get("layers");
            List<Neuron[]> layers = new ArrayList<>();

            for (int i = 0; i < layersData.size(); i++) {
                List<Map<String, Object>> layerData = layersData.get(i);
                Neuron[] layer = new Neuron[layerData.size()];
                int j = 0;
                for (Map<String, Object> neuronData : layerData) {
                    double[] weights = convertToDoubleArray((List<Double>) neuronData.get("weights"));
                    double bias = ((Number) neuronData.get("bias")).doubleValue();
                    double output = ((Number) neuronData.get("output")).doubleValue();

                    // Récupérer les entrées (neurones de la couche précédente)
                    Neuron[] inputs = i == 0 ? new Neuron[0] : layers.get(i - 1);
                    Neuron neuron = new Neuron(inputs, weights, bias);

                    neuron.setOutput(output);
                    layer[j] = neuron;
                    j++;
                }
                layers.add(layer);
            }

            System.out.println("Modèle chargé avec succès. Score : " + score + ", Couches : " + layers.size());

            NeuralNetwork loadedNetwork = new NeuralNetwork(layers);
            loadedNetwork.setScore(score); // Charger le score dans le modèle
            return loadedNetwork;
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier du modèle : " + e.getMessage());
            return null;
        }
    }


    private static List<Map<String, Object>> getMapList(List<Neuron> layer) {
        List<Map<String, Object>> layerData = new ArrayList<>();
        for (Neuron neuron : layer) {
            Map<String, Object> neuronData = new HashMap<>();

            // Vérifier si les poids sont NaN avant de les ajouter
            double[] sanitizedWeights = new double[neuron.getWeights().length];
            for (int i = 0; i < neuron.getWeights().length; i++) {
                sanitizedWeights[i] = Double.isNaN(neuron.getWeights()[i]) ? 0.0 : neuron.getWeights()[i];
            }

            // Vérifier si le biais est NaN avant de l'ajouter
            double sanitizedBias = Double.isNaN(neuron.getBias()) ? 0.0 : neuron.getBias();

            // Vérifier si la sortie est NaN avant de l'ajouter
            double sanitizedOutput = Double.isNaN(neuron.getOutput()) ? 0.0 : neuron.getOutput();

            neuronData.put("weights", convertToList(sanitizedWeights)); // Conversion en liste pour la sérialisation
            neuronData.put("bias", sanitizedBias);
            neuronData.put("output", sanitizedOutput);
            layerData.add(neuronData);
        }
        return layerData;
    }

    // Conversion d'un tableau double[] en List<Double>
    private static List<Double> convertToList(double[] array) {
        List<Double> list = new ArrayList<>();
        for (double d : array) {
            list.add(d);
        }
        return list;
    }

    // Conversion d'une List<Double> en tableau double[]
    private static double[] convertToDoubleArray(List<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
