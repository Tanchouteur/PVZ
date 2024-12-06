package fr.tanchou.pvz.guiJavaFx;

import fr.tanchou.pvz.PVZ;
import fr.tanchou.pvz.guiJavaFx.assetsLoder.AssetsLoader;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PartieControllerView {
    private final PVZ gameInstance;
    private  GameBoard gameBoard;
    private AnimationTimer graphicGameLoop;

    public PartieControllerView(PVZ gameInstance) {
        this.gameInstance = gameInstance;
    }

    public void start(Stage primaryStage) {
        int width = 1920;
        int height = 1080;
        AssetsLoader assetsLoader = new AssetsLoader();
        gameBoard = new GameBoard(width, height, gameInstance.getPartie(), assetsLoader);
        Scene scene = new Scene(gameBoard, width, height);

        primaryStage.setTitle("Plants vs Zombies");

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> stop());
        primaryStage.setResizable(false);
        primaryStage.setMaximized(true);
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
        gameBoard.update();
    }

    public void stop() {
        if (graphicGameLoop != null) {
            graphicGameLoop.stop();
        }
    }
}
