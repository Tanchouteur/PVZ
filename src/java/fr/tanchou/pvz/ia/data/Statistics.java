package fr.tanchou.pvz.ia.data;

import fr.tanchou.pvz.ia.GenerationManager;
import fr.tanchou.pvz.ia.network.NeuralNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Statistics {
    private final List<Map<String, Integer>> scoresHistory = new LinkedList<>();

    // Méthode pour afficher la moyenne des scores d'une génération
    public void printAverageGenerationScore(List<NeuralNetwork> models) {
        int averageScore = computeAverageGenerationScore(models);
        System.out.println("Average score of the generation: " + averageScore);
    }

    // Méthode pour sauvegarder l'historique des scores pour une génération
    public void saveScoresHistory(GenerationManager generationManager) {
        scoresHistory.add(Map.of(
                "scoreBestModel", generationManager.getBestModels().get(0).getScore(),
                "averageScore", computeAverageGenerationScore(generationManager.getAllModels()),
                "mutationAmplitude", generationManager.getMutationAmplitude()
        ));
    }

    // Méthode pour calculer la moyenne des scores d'une génération
    public int computeAverageGenerationScore(List<NeuralNetwork> models) {

        if (models.isEmpty()) {
            throw new IllegalArgumentException("Models list is empty");
        }

        int totalScore = 0;
        for (NeuralNetwork model : models) {
            totalScore += model.getScore();
        }
        return models.isEmpty() ? 0 : totalScore / models.size();
    }

    // Méthode pour obtenir l'historique des scores
    public List<Map<String, Integer>> getScoresHistory() {
        return scoresHistory;
    }

    // Méthode pour calculer la moyenne des scores des moyennes des générations
    public double computeAverageOfGenerationAverages() {
        return scoresHistory.stream()
                .mapToInt(map -> map.getOrDefault("averageScore", 0))
                .average()
                .orElse(0.0);
    }

    // Méthode pour vérifier si la dernière génération est meilleure que la précédente
    public boolean isCurrentGenerationBetter() {
        if (scoresHistory.size() < 2) return false;
        int lastAverage = scoresHistory.get(scoresHistory.size() - 1).get("averageScore");
        int previousAverage = scoresHistory.get(scoresHistory.size() - 2).get("averageScore");
        return lastAverage > previousAverage;
    }

    // Méthode pour afficher proprement l'historique des scores
    public void printScoresHistory() {
        System.out.println("\n===== Score History =====");
        for (int i = 0; i < scoresHistory.size(); i++) {
            Map<String, Integer> stats = scoresHistory.get(i);
            System.out.printf("Generation %d: Best Score = %d, Average Score = %d, Mutation Amplitude = %d\n",
                    i + 1,
                    stats.getOrDefault("scoreBestModel", 0),
                    stats.getOrDefault("averageScore", 0),
                    stats.getOrDefault("mutationAmplitude", 0)/100);
        }
        System.out.println("========================\n");
    }

    // Méthode pour écrire l'historique des scores dans un fichier
    public void saveHistoryToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Generation, Best Score, Average Score, Mutation Amplitude\n");
            for (int i = 0; i < scoresHistory.size(); i++) {
                Map<String, Integer> stats = scoresHistory.get(i);
                writer.write(String.format("%d, %d, %d, %d\n",
                        i + 1,
                        stats.getOrDefault("scoreBestModel", 0),
                        stats.getOrDefault("averageScore", 0),
                        stats.getOrDefault("mutationAmplitude", 0)));
            }
        } catch (IOException e) {
            System.err.println("Error writing history to file: " + e.getMessage());
        }
    }

    public void loadScoresHistoryFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    String key1 = values[0].split("=")[0];
                    int value1 = Integer.parseInt(values[0].split("=")[1]);
                    String key2 = values[1].split("=")[0];
                    int value2 = Integer.parseInt(values[1].split("=")[1]);
                    String key3 = values[2].split("=")[0];
                    int value3 = Integer.parseInt(values[2].split("=")[1]);

                    scoresHistory.add(Map.of(
                            key1, value1,
                            key2, value2,
                            key3, value3
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erreur lors du chargement de l'historique depuis le fichier : " + e.getMessage());
        }
    }

    // Méthode pour trouver la meilleure génération
    public Map<String, Integer> getBestGeneration() {
        return scoresHistory.stream()
                .max(Comparator.comparingInt(map -> map.getOrDefault("scoreBestModel", 0)))
                .orElse(Map.of());
    }

    // Méthode pour afficher les statistiques globales
    public void printGlobalStatistics() {
        Map<String, Integer> bestGen = getBestGeneration();
        System.out.println("\n===== Global Statistics =====");
        System.out.println("Average of all generations' averages: " + computeAverageOfGenerationAverages());
        System.out.println("Best generation: " + (scoresHistory.indexOf(bestGen) + 1));
        System.out.println("Best score overall: " + bestGen.getOrDefault("scoreBestModel", 0));
        System.out.println("============================\n");
    }
}