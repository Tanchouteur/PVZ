package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.game.PVZ;
import fr.tanchou.pvz.game.Player;
import fr.tanchou.pvz.ia.network.GameAI;
import fr.tanchou.pvz.ia.network.NeuralNetwork;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class IAEnvironmentManager {
    private final ExecutorService executorService; // Pool de threads pour les simulations
    private final AtomicInteger completedGames = new AtomicInteger(0);

    public IAEnvironmentManager(ExecutorService executorService) {
        this.executorService = executorService;
    }

    // Initialise les jeux
    public void initializeGames(List<NeuralNetwork> models) {
        System.out.println("Init & Lancement des simulations...");

        for (int i = 0; i < models.size(); i++) {

            Player iaPlayer = new Player("IA_Player_" + (i + 1));

            executorService.submit(getRunnableInstance(models, iaPlayer, i));
        }

        System.out.println("Toutes les simulations sont lancées.");
    }

    private Runnable getRunnableInstance(List<NeuralNetwork> models, Player iaPlayer, int i) {
        PVZ pvz = new PVZ(iaPlayer, new GameAI(models.get(i))); // Attribution du modèle à l'IA

        return () -> {
            try {
                pvz.runManualGame();
                models.get(i).setScore((int) Math.round(iaPlayer.calculateScore()));
                completedGames.incrementAndGet();
            } catch (Exception e) {
                System.err.println("Erreur dans la simulation du modèle " + i + ": " + e.getMessage());
            }
        };
    }
}
