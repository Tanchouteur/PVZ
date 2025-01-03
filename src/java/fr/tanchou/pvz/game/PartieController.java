package fr.tanchou.pvz.game;

import fr.tanchou.pvz.ia.network.GameAI;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PartieController {
    private final ScheduledExecutorService gameLoop;
    private final Partie partie;
    private final GameAI gameAI;

    private int totalTick = 0;

    public PartieController(Partie partie, GameAI gameAI) {
        this.partie = partie;
        this.gameAI = gameAI;

        gameLoop = Executors.newSingleThreadScheduledExecutor();
    }

    // Mettre à jour le modèle
    public void update() {
        totalTick++;

        //System.out.println("Before take aktion: " + totalTick);

        if (gameAI != null && totalTick % 2 == 0) {
            try {
                gameAI.takeAction(partie);
            }catch (Exception e) {
                System.err.println("Erreur lors de la prise d'action de l'IA: " + e);
                e.printStackTrace(); // Affiche la pile d'exécution complète
            }
        }

        //System.out.println("After take aktion: " + totalTick);

        partie.update();

        if (partie.isDefeated()) {
            stopGame();
            //System.out.println("Game Over !");
        }else if (partie.isVictory()) {
            stopGame();
            //System.out.println("Victory !");
        }
    }

    // Démarrer le jeu
    public void startGame(double multiplicateurOfSpeed) {
        // Mettre à jour le modèle pour avoir 24 ticks par seconde
        gameLoop.scheduleAtFixedRate(this::update, 0, 1000 / Math.round(10*multiplicateurOfSpeed), TimeUnit.MILLISECONDS);
    }

    // Arrêter le jeu
    public void stopGame() {
        //System.out.println("player Score : " + partie.getPlayer().calculateScore());
        gameLoop.shutdown();
    }
}
