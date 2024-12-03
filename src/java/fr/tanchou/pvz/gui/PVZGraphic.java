package fr.tanchou.pvz.gui;

import fr.tanchou.pvz.PVZ;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

        // Menu principal
        VBox menu = new VBox();
        Button startButton = new Button("Commencer la Partie");
        startButton.setOnAction(event -> startGame(primaryStage));

        Button exitButton = new Button("Quitter");
        exitButton.setOnAction(event -> primaryStage.close());

        menu.getChildren().addAll(startButton, exitButton);
        root.setCenter(menu);

        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setTitle("Plants vs Zombies");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startGame(Stage primaryStage) {
        // Passer à l'écran de jeu
        PartieControllerView controllerView = new PartieControllerView(pvzInstance);
        controllerView.start(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        // Arrêter la partie proprement si la fenêtre est fermée
        pvzInstance.stopGame();
        super.stop();
    }
}
