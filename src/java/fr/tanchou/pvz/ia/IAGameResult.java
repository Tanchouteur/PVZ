package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.rowComponent.Row;
import fr.tanchou.pvz.ia.network.NeuralNetwork;

public class IAGameResult {
    private final NeuralNetwork neuralNetwork;
    private final boolean victory;
    private final int ticksSurvived;
    private final Partie partie;

    public IAGameResult( NeuralNetwork neuralNetwork, Partie partie) {

        this.neuralNetwork = neuralNetwork;

        this.partie = partie;

        this.victory = partie.isVictory();
        this.ticksSurvived = partie.getZombieSpawner().getTotalTick();
    }

    @Override
    public String toString() {
        return "IA : " + (victory ? "Victoire" : "Défaite") + ", Ticks: " + ticksSurvived;
    }

    public Partie getPartie() {
        return partie;
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public int calculateScore() {
        int mowers = 1;
        for (Row row : partie.getRows()) {
            if (row.getMower() != null) {
                mowers *= 2;
            }
        }

        // Scores individuels
        int mowersScore = mowers * 50; // Conservation des tondeuses.
        int survivalScore = ticksSurvived * 2; // Temps de survie avec un poids réduit.
        //int zombieKillScore = zombiesKilled * 5; // Récompense pour chaque zombie tué.
        //int penalty = zombiesPassed * -50; // Pénalité pour chaque zombie passé.
        int plantPlacementScore = partie.getPlayer().getPlantPlacedCount() * 5; // Récompense pour les plantes posées.

        // Score total
        return survivalScore + mowersScore + plantPlacementScore + (victory ? 1000 : 0);
    }

}