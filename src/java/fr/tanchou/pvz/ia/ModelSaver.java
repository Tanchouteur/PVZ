package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.ia.network.NeuralNetwork;
import fr.tanchou.pvz.ia.network.Neuron;
import com.google.gson.*;
import java.io.*;
import java.util.*;

public class ModelSaver {

    // Méthode pour sauvegarder un modèle
    public static void saveModel(NeuralNetwork network, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Structure des couches et des poids
        List<List<Map<String, Object>>> layersData = new ArrayList<>();
        for (List<Neuron> layer : network.getLayers()) {
            List<Map<String, Object>> layerData = new ArrayList<>();
            for (Neuron neuron : layer) {
                Map<String, Object> neuronData = new HashMap<>();
                neuronData.put("weights", neuron.getWeights());
                neuronData.put("output", neuron.getOutput());
                layerData.add(neuronData);
            }
            layersData.add(layerData);
        }

        // Convertir en JSON
        String json = gson.toJson(layersData);

        // Écrire dans un fichier
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier du modèle : " + e.getMessage());
        }
    }

    // Méthode pour charger un modèle
    public static NeuralNetwork loadModel(String filePath) {
        Gson gson = new Gson();

        // Lire le fichier JSON
        try (Reader reader = new FileReader(filePath)) {
            // Convertir le JSON en structure de données Java
            List<List<Map<String, Object>>> layersData = gson.fromJson(reader, List.class);

            List<List<Neuron>> layers = new ArrayList<>();

            // Reconstruire le réseau
            for (int i = 0; i < layersData.size(); i++) {
                List<Map<String, Object>> layerData = layersData.get(i);
                List<Neuron> layer = new ArrayList<>();
                for (Map<String, Object> neuronData : layerData) {
                    List<Double> weights = (List<Double>) neuronData.get("weights");
                    double output = ((Number) neuronData.get("output")).doubleValue();

                    // Récupérer les entrées (neurones de la couche précédente)
                    List<Neuron> inputs = i == 0 ? new ArrayList<>() : layers.get(i - 1);
                    Neuron neuron = new Neuron(inputs, weights);
                    neuron.setOutput(output);
                    layer.add(neuron);
                }
                layers.add(layer);
            }

            System.out.println("Modèle chargé avec succès. Couches : " + layers.size());
            return new NeuralNetwork(layers);
        }catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier du modèle : " + e.getMessage());
            return null;
        }
    }
}
