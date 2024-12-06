package fr.tanchou.pvz.guiJavaFx;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.guiJavaFx.assetsLoder.AssetsLoader;
import fr.tanchou.pvz.guiJavaFx.layers.game.EntityLayer;
import fr.tanchou.pvz.guiJavaFx.layers.ihm.GameInfoLayer;
import fr.tanchou.pvz.guiJavaFx.layers.ihm.PlayerLayer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class GameBoard extends Pane {
    private final PlayerLayer playerLayer;
    private final EntityLayer entityLayer;
    private final GameInfoLayer gameInfoLayer;

    public GameBoard(int width, int height, Partie partie, AssetsLoader assetsLoader) {
        super();
        this.setPrefSize(width, height);

        addBackground();
        this.gameInfoLayer = new GameInfoLayer(partie.getZombieSpawner());
        this.playerLayer = new PlayerLayer(width, height, partie, assetsLoader);
        this.entityLayer = new EntityLayer(width, height, partie, assetsLoader);

        this.getChildren().add(gameInfoLayer);
        this.getChildren().add(playerLayer);
        this.getChildren().add(entityLayer);

        gameInfoLayer.setMouseTransparent(true);
        playerLayer.setMouseTransparent(false);
    }

    private void addBackground() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/terrains/dayAdapted.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(this.getPrefWidth());
        imageView.setFitHeight(this.getPrefHeight());
        this.getChildren().add(imageView);
    }

    public void update() {
        gameInfoLayer.update();
        playerLayer.update();
        entityLayer.update();
    }

}
