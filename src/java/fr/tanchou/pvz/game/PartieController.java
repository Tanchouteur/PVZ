package fr.tanchou.pvz.game;

import fr.tanchou.pvz.ia.GameAI;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PartieController {
    private final ScheduledExecutorService gameLoop;
    private final Partie partie;
    private final GameAI gameAI;

    private int totalTick = 0;

    public PartieController(Partie partie, Boolean ia) {
        this.partie = partie;

        if (ia) {
            this.gameAI = new GameAI();
        } else {
            this.gameAI = null;
        }

        gameLoop = Executors.newSingleThreadScheduledExecutor();
    }

    // Mettre à jour le modèle
    public void update() {
        totalTick++;

        //System.out.println("Tick: " + totalTick);

        if (gameAI != null && totalTick % 2 == 0) {
            gameAI.takeAction(partie);
        }

        partie.update();

        if (partie.isDefeated()) {
            stopGame();
            System.out.println("Game Over");
        }else if (partie.isVictory()) {
            stopGame();
            System.out.println("Victory");
        }
    }

    // Démarrer le jeu
    public void startGame() {
        // Mettre à jour le modèle pour avoir 24 ticks par seconde
        gameLoop.scheduleAtFixedRate(this::update, 0, 1000 / 10, TimeUnit.MILLISECONDS);
    }

    // Arrêter le jeu
    public void stopGame() {
        gameLoop.shutdown();
    }

    public int getTickCount() {
        return totalTick;
    }
}
