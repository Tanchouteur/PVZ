package fr.tanchou.pvz.ia.utils;

import fr.tanchou.pvz.game.PVZ;
import fr.tanchou.pvz.game.Player;
import fr.tanchou.pvz.ia.network.GameAI;
import fr.tanchou.pvz.ia.network.NeuralNetwork;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class IAEnvironmentManager {
    private ExecutorService executorService; // Pool de threads pour les simulations
    private final AtomicInteger completedGames = new AtomicInteger(0);
    private int numberOfGames; // Nombre de jeux à simuler

    public IAEnvironmentManager() {
    }

    // Initialise les jeux
    public void initializeGames(List<NeuralNetwork> models, boolean random, int nbThreadToUse) {
        this.executorService = Executors.newFixedThreadPool(nbThreadToUse);
        System.out.println("Init & Lancement des simulations...");
        this.numberOfGames = models.size()-1;
        this.completedGames.set(0);

        for (int i = 0; i < models.size(); i++) {

            Player iaPlayer = new Player("IA_Player_" + (i + 1));

            executorService.submit(getRunnableInstance(models, iaPlayer, i, random));
        }

        System.out.println("Toutes les simulations sont lancées.");
    }

    private Runnable getRunnableInstance(List<NeuralNetwork> models, Player iaPlayer, int i, boolean random) {
        PVZ pvz = new PVZ(iaPlayer, new GameAI(models.get(i)), random); // Attribution du modèle à l'IA

        return () -> {
            try {
                pvz.runManualGame();
                completedGames.incrementAndGet();
            } catch (Exception e) {
                System.err.println("Erreur dans la simulation du modèle " + i + ": " + e.getMessage());
            }
        };
    }

    public boolean areAllSimulationsCompleted() {
        return completedGames.get() >= numberOfGames ;
    }

    public int getPrcntgOfSimulationsCompleted() {
        return (int) (((double) completedGames.get() / numberOfGames) * 100);
    }
}
