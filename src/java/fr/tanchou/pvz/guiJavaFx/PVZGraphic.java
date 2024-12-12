package fr.tanchou.pvz.guiJavaFx;

import fr.tanchou.pvz.game.PVZ;
import fr.tanchou.pvz.guiJavaFx.assetsLoder.AssetsLoader;
import fr.tanchou.pvz.guiJavaFx.sound.SoundManager;
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

    private SoundManager soundManager;

    private boolean ia = true;

    public static void launchView(PVZ pvz) {
        pvzInstance = pvz;
        launch();

    }

    @Override
    public void start(Stage primaryStage) {
        soundManager = new SoundManager();
        initializeSounds();

        soundManager.playBackgroundMusic("/sounds/menu/MainMenu.mp3");

        BorderPane root = new BorderPane();
        AssetsLoader assetsLoader = new AssetsLoader(pvzInstance.getPlayer());

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

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/bg.png")));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(1920);
        imageView.setFitHeight(1080);

        menu.getChildren().add(imageView);

        menu.getChildren().addAll(startButton, exitButton);
        root.setCenter(menu);

        Scene scene = new Scene(root, 1920, 1080);
        primaryStage.setTitle("Plants vs Zombies");
        primaryStage.setScene(scene);
        //primaryStage.setMaximized(false);

        primaryStage.show();

    }

    private void startGame(Stage primaryStage) {
        // Passer à l'écran de jeu
        soundManager.playSound("buttonclick");
        pvzInstance.startGame(false);

        PartieControllerView controllerView = new PartieControllerView(pvzInstance, soundManager);
        controllerView.start(primaryStage);
        soundManager.playBackgroundMusic("/sounds/InGame.mp3");
    }

    @Override
    public void stop() throws Exception {
        // Arrêter la partie proprement si la fenêtre est fermée
        pvzInstance.stopGame();
        soundManager.stopBackgroundMusic();
        soundManager.playSound("buttonclick");
        super.stop();
    }

    private void initializeSounds() {
        soundManager.loadSound("zombieGroan1", "/sounds/zombie/Groan.mp3");
        soundManager.loadSound("zombieGroan2", "/sounds/zombie/Groan2.mp3");
        soundManager.loadSound("zombieEatPlant", "/sounds/zombie/Gulp.mp3");
        soundManager.loadSound("waveStart", "/sounds/zombie/WaveStart.mp3");
        soundManager.loadSound("zombieBit", "/sounds/zombie/ZombieBite.mp3");
        soundManager.loadSound("plant", "/sounds/plant/Plant.mp3");
        soundManager.loadSound("buttonclick", "/sounds/menu/Buttonclick.mp3");
        soundManager.loadSound("mainMenu", "/sounds/menu/MainMenu.mp3");
        soundManager.loadSound("inGame", "/sounds/InGame.mp3");
        soundManager.loadSound("throw", "/sounds/plant/Throw.mp3");
        soundManager.loadSound("splat", "/sounds/plant/Splat.mp3");
        soundManager.loadSound("splat", "/sounds/game/Shovel.mp3");
    }
}
