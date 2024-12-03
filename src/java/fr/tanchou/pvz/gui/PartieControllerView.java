package fr.tanchou.pvz.gui;

import fr.tanchou.pvz.PVZ;
import fr.tanchou.pvz.game.Partie;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PartieControllerView {
    private final PVZ gameInstance;
    private AnimationTimer graphicGameLoop;

    public PartieControllerView(PVZ gameInstance) {
        this.gameInstance = gameInstance;
    }

    public void start(Stage primaryStage) {
        Pane board = new Board();

        Scene scene = new Scene(board, 1920, 1080);

        primaryStage.setTitle("Plants vs Zombies");

        primaryStage.setScene(scene);

        primaryStage.show();

        startViewLoop();
    }

    private void startViewLoop() {
        graphicGameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateView();
            }
        };
        graphicGameLoop.start();
    }

    //uniquement des observeur on ne touche pas au model on le regarde
    private void updateView() {
    }

    public void stop() {
        if (graphicGameLoop != null) {
            graphicGameLoop.stop();
        }
    }
}
