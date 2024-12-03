package fr.tanchou.pvz.gui;

import fr.tanchou.pvz.PVZ;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PVZGraphic extends Application {
    private static PVZ pvzInstance;

    public static void launchView(PVZ pvz) {
        pvzInstance = pvz;
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        root.setCenter(createGameBoard());

        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setTitle("Plants vs Zombies");
        primaryStage.setScene(scene);
        primaryStage.show();

        pvzInstance.startGame(false);
    }

    private BorderPane createGameBoard() {
        // Exemple : Créer une grille pour représenter le plateau
        BorderPane gameBoard = new BorderPane();
        // Ajouter des éléments pour les plantes, zombies, etc.
        return gameBoard;
    }

    @Override
    public void stop() throws Exception {
        // Arrêter la partie proprement si la fenêtre est fermée
        pvzInstance.stopGame();
        super.stop();
    }
}
