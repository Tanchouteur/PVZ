package fr.tanchou.pvz.guiJavaFx;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.guiJavaFx.layers.game.EntityLayer;
import fr.tanchou.pvz.guiJavaFx.layers.ihm.PlayerLayer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class GameBoard extends Pane {
    private final PlayerLayer playerLayer;
    private final EntityLayer entityLayer;

    public GameBoard(int width, int height, Partie partie) {
        super();
        this.setPrefSize(width, height);

        addBackground();

        this.playerLayer = new PlayerLayer(width, height, partie);
        this.entityLayer = new EntityLayer(width, height, partie);


        this.getChildren().add(playerLayer);
        this.getChildren().add(entityLayer);
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
        playerLayer.update();
        entityLayer.update();
    }

}
