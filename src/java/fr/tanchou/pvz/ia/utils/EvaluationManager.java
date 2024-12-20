package fr.tanchou.pvz.ia.utils;

import fr.tanchou.pvz.ia.network.NeuralNetwork;

import java.util.List;

public class EvaluationManager {
    private int currentAverageScore = 0;
    private int previousAverageScore = 0;
    private int deltaScore = 0;

    private boolean randomLevel = false;

    private double mutationAmplitude = 1.0;

    boolean chooseRandomLevel(NeuralNetwork model, int generationNumber) {
        this.randomLevel = !this.randomLevel && generationNumber > 10 && this.deltaScore < 150 && model.getScore() > 12000;
        return this.randomLevel;
    }

    double computeStatistics(List<NeuralNetwork> models, int generationNumber) {

        // Calcul du score normalisé du meilleur modèle
        int bestScore = models.isEmpty() ? 0 : (int) models.getFirst().getScore();
        //double maxOptimalScore = 17000 ; // Score théorique maximal

        // Calcul du score moyen des modèles
        this.currentAverageScore = (int) models.stream()
                .mapToDouble(NeuralNetwork::getScore)
                .average()
                .orElse(0);

        // Calcul du delta de score
        this.deltaScore = (this.currentAverageScore - this.previousAverageScore);

        this.previousAverageScore = this.currentAverageScore;

        // Calcul de la mutation en fonction du score normalisé
        this.computeMutationAmplitude(bestScore, this.currentAverageScore, generationNumber);

        double bestToAverageRatio = (double) bestScore / this.currentAverageScore;
        System.out.println("=========== Statistiques ===========");
        System.out.println("Best Score: " + bestScore + ", Average Score: " + this.currentAverageScore + ", Ratio best/average: " + bestToAverageRatio + ", " +
                "\ndelta with previous gen :" + this.deltaScore + ", Mutation amplitude: " + this.mutationAmplitude);

        return this.mutationAmplitude;

    }

    // Méthode pour récupérer les statistiques
    public String getStatistics(double bestScore) {
        return bestScore + " " + this.deltaScore + " " + this.currentAverageScore + " " + this.previousAverageScore + " " + this.randomLevel;
    }

    private void computeMutationAmplitude(double bestScore, double averageScore, int generationNumber) {
        // Normalisation des scores par rapport au maximum attendu (18 000)
        double maxScore = 18000.0;
        double normalizedBestScore = bestScore / maxScore;
        double normalizedAverageScore = averageScore / maxScore;

        // Calcul du ratio
        double bestToAverageRatio = normalizedBestScore / normalizedAverageScore;

        // Décroissance exponentielle contrôlée
        double initialAmplitude = 2.0; // Amplitude de départ
        double lambda = 0.1; // Contrôle la vitesse de décroissance
        double baseAmplitude = initialAmplitude * Math.exp(-lambda * generationNumber);

        // Ajustement en fonction du ratio normalisé
        if (bestToAverageRatio < 1.2) {
            baseAmplitude *= 1.3; // Exploration accrue si les scores sont proches
        } else if (bestToAverageRatio > 1.8) {
            baseAmplitude *= 0.7; // Réduction si domination trop forte des meilleurs
        }

        // Ajustement en fonction du delta de score
        if (this.deltaScore < 100) { // Peu de progression
            baseAmplitude *= 1.1;
        } else if (this.deltaScore > 500) { // Bonne progression
            baseAmplitude *= 0.9;
        }

        // Ajustement selon la performance actuelle (exemple basé sur 10k+)
        if (bestScore > 10000 && bestScore < maxScore * 0.9) {
            baseAmplitude *= 1.05; // Encourage la mutation si proche du plafond
        }

        // Plafonnement réaliste
        this.mutationAmplitude = Math.max(0.005, Math.min(baseAmplitude, 2.0));
    }

    public boolean needTraining() {
        return (deltaScore > 100 || !this.randomLevel);
    }
}
