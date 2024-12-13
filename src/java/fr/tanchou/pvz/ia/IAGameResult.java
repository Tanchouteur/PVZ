package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.game.Partie;
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

    public double getScore() {
        return partie.getPlayer().calculateScore();
    }

}