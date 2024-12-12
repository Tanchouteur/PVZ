package fr.tanchou.pvz.ia;

import fr.tanchou.pvz.PVZ;
import fr.tanchou.pvz.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class IAEnvironmentManager {
    private final int numberOfGames; // Nombre d'instances IA
    private final ExecutorService executorService; // Pool de threads pour les simulations
    private final List<Future<IAGameResult>> gameResults;
    private final AtomicInteger completedGames = new AtomicInteger(0);

    public IAEnvironmentManager(int numberOfGames) {
        this.numberOfGames = numberOfGames;
        this.executorService = Executors.newFixedThreadPool(numberOfGames);
        this.gameResults = new ArrayList<>();
    }

    // Initialise les jeux sans les démarrer
    public void initializeGames() {
        for (int i = 0; i < numberOfGames; i++) {
            Player iaPlayer = new Player("IA_Player_" + (i + 1));
            PVZ pvz = new PVZ(iaPlayer);

            Callable<IAGameResult> simulationTask = () -> {
                pvz.runManualGame(4500, true); // Limite de 280 ticks pour chaque simulation

                IAGameResult result = new IAGameResult(
                        iaPlayer.getName(),
                        pvz.getPartie().isVictory(),
                        pvz.getPartieController().getTickCount()
                );

                completedGames.incrementAndGet();
                return result;
            };

            gameResults.add(executorService.submit(simulationTask));
        }
    }

    // Démarre les simulations
    public void startSimulations() {
        System.out.println("Début des simulations pour " + numberOfGames + " IA.");
    }

    // Stoppe toutes les simulations en attente (si besoin)
    public void stopSimulations() {
        executorService.shutdownNow();
        System.out.println("Toutes les simulations sont arrêtées.");
    }

    // Collecte les résultats des simulations
    public List<IAGameResult> collectResults() {
        List<IAGameResult> results = new ArrayList<>();

        for (Future<IAGameResult> future : gameResults) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        return results;
    }

    public boolean areAllSimulationsCompleted() {
        return completedGames.get() == numberOfGames;
    }
}
