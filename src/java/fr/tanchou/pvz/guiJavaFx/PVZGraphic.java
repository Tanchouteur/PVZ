package fr.tanchou.pvz.guiJavaFx;

import fr.tanchou.pvz.PVZ;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

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
        Pane menu = new Pane();
        menu.setPrefSize(1920, 1080);
        Button startButton = new Button("Commencer la Partie");
        startButton.setOnAction(event -> startGame(primaryStage));

        Button exitButton = new Button("Quitter");
        exitButton.setOnAction(event -> primaryStage.close());

        exitButton.setLayoutX(920);
        exitButton.setLayoutY(500);

        startButton.setLayoutX(880);
        startButton.setLayoutY(400);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/bg.png")));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(1920);
        imageView.setFitHeight(1080);

        menu.getChildren().add(imageView);

        menu.getChildren().addAll(startButton, exitButton);
        root.setCenter(menu);

        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setTitle("Plants vs Zombies");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);

        primaryStage.show();

    }

    private void startGame(Stage primaryStage) {
        // Passer à l'écran de jeu
        pvzInstance.startGame(true);
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
