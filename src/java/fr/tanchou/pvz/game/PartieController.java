package fr.tanchou.pvz.game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class PartieController {
    private final ScheduledExecutorService gameLoop;
    private final Partie partie;

    public PartieController(Partie partie) {
        this.partie = partie;
        // Initialiser le loop pour les ticks
        gameLoop = Executors.newSingleThreadScheduledExecutor();
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

    // Mettre à jour le modèle
    private void update() {
        partie.update();

        if (partie.isDefeated()) {
            stopGame();
            System.out.println("Game Over");
        }
    }
}
