package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.rowComponent.Row;
import fr.tanchou.pvz.ia.network.NeuralNetwork;

public class IAGameResult {
    private final String iaName;
    private final NeuralNetwork neuralNetwork;
    private final boolean victory;
    private final int ticksSurvived;
    private final Partie partie;

    public IAGameResult(String iaName, NeuralNetwork neuralNetwork, Partie partie) {
        this.iaName = iaName;
        this.neuralNetwork = neuralNetwork;

        this.partie = partie;

        this.victory = partie.isVictory();
        this.ticksSurvived = partie.getZombieSpawner().getTotalTick();
    }

    public String getIaName() {
        return iaName;
    }

    public boolean isVictory() {
        return victory;
    }

    public int getTicksSurvived() {
        return ticksSurvived;
    }

    @Override
    public String toString() {
        return "IA " + iaName + ": " + (victory ? "Victoire" : "Défaite") + ", Ticks: " + ticksSurvived;
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
        return ticksSurvived*100 + (victory ? 1000 : 0) + mowers * 100;
    }
}