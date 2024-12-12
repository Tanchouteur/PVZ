package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.ia.network.NeuralNetwork;
import fr.tanchou.pvz.ia.network.Neuron;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ModelSaver {

    // Sauvegarder un réseau neuronal dans un fichier
    public void saveModel(NeuralNetwork network, String filename) {
        try (FileWriter file = new FileWriter(filename)) {
            JSONObject modelData = new JSONObject();

            // Sauvegarder la structure du réseau : les couches et les neurones
            JSONArray layersArray = new JSONArray();
            for (List<Neuron> layer : network.getLayers()) {
                JSONArray layerArray = new JSONArray();
                for (Neuron neuron : layer) {
                    // Sauvegarder les poids des neurones
                    JSONArray weightsArray = new JSONArray(neuron.getWeights());
                    layerArray.put(weightsArray);
                }
                layersArray.put(layerArray);
            }
            modelData.put("layers", layersArray);

            // Écrire dans le fichier
            file.write(modelData.toString());
            System.out.println("Modèle sauvegardé dans " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Charger un réseau neuronal à partir d'un fichier
    public NeuralNetwork loadModel(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            JSONObject modelData = new JSONObject(jsonContent.toString());
            JSONArray layersArray = modelData.getJSONArray("layers");

            List<List<Neuron>> layers = new ArrayList<>();
            for (int i = 0; i < layersArray.length(); i++) {
                JSONArray layerArray = layersArray.getJSONArray(i);
                List<Neuron> layer = new ArrayList<>();

                for (int j = 0; j < layerArray.length(); j++) {
                    JSONArray weightsArray = layerArray.getJSONArray(j);
                    List<Double> weights = new ArrayList<>();
                    for (int k = 0; k < weightsArray.length(); k++) {
                        weights.add(weightsArray.getDouble(k));
                    }
                    layer.add(new Neuron(weights));  // Créer un neurone avec les poids chargés
                }
                layers.add(layer);
            }

            return new NeuralNetwork(layers);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
