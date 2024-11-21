package fr.tanchou.pvz.game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.tanchou.pvz.PartieVue;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

public class PartieController {
    private final ScheduledExecutorService gameLoop; // Pour gérer les ticks
    private final AnimationTimer renderLoop; // Pour gérer le rafraîchissement

    private final Partie partie; // Modèle du jeu
    private final PartieVue vue; // Vue du jeu

    public PartieController(Partie partie, PartieVue vue) {
        this.partie = partie;
        this.vue = vue;

        // Initialiser le loop pour les ticks
        gameLoop = Executors.newSingleThreadScheduledExecutor();

        // Initialiser le timer pour la vue
        renderLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateView(); // Met à jour l'affichage
            }
        };
    }

    // Démarrer le jeu
    public void startGame() {
        // Démarrer la logique du modèle avec un tick toutes les 100 ms
        gameLoop.scheduleAtFixedRate(() -> {
            Platform.runLater(this::updateModel); // Synchronisation avec le thread JavaFX
        }, 0, 16, TimeUnit.MILLISECONDS);

        // Démarrer le rafraîchissement graphique
        renderLoop.start();
    }

    // Arrêter le jeu
    public void stopGame() {
        gameLoop.shutdown();
        renderLoop.stop();
    }

    // Mise à jour de la logique (modèle)
    private void updateModel() {
        partie.update(); // Méthode qui avance la logique dans le modèle
    }

    // Mise à jour de la vue
    private void updateView() {
        vue.update(partie); // Transfère les données du modèle vers la vue
    }
}
