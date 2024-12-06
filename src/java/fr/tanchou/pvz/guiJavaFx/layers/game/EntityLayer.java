package fr.tanchou.pvz.guiJavaFx.layers.game;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.guiJavaFx.assetsLoder.AssetsLoader;
import javafx.scene.layout.Pane;

public class EntityLayer extends Pane {
    private final PlantLayer plantLayer;
    private final ZombieLayer zombieLayer;
    private final BulletLayer bulletLayer;
    private final MawerPanel mawerLayer;

    public EntityLayer(double width, double height, Partie partie, AssetsLoader assetsLoader) {
        super();
        //0.771w 0.919h
        // decalage x 0.1739 par rapprt a la largeur du panel parent
        // decalage y 0.0574 par rapport a la hauteur du panel parent

        this.setPrefSize(width*(0.771+0.1739), height*0.84);
        this.setLayoutX(width*0.1739);
        this.setLayoutY(height*0.11);

        //this.setStyle("-fx-background-color: rgba(202,22,22,0.37);");

        this.plantLayer = new PlantLayer(width, height, partie, assetsLoader);
        this.zombieLayer = new ZombieLayer(this.getPrefWidth(), this.getPrefHeight(), partie.getRows(), assetsLoader);
        this.bulletLayer = new BulletLayer(this.getPrefWidth(), this.getPrefHeight(), partie.getRows(), assetsLoader.getAssetsLoaded());
        this.mawerLayer = new MawerPanel(this.getPrefWidth(), this.getPrefHeight(), partie.getRows(), assetsLoader.getAssetsLoaded().get("mower"));

        zombieLayer.setMouseTransparent(true);
        bulletLayer.setMouseTransparent(true);
        mawerLayer.setMouseTransparent(true);
        plantLayer.setMouseTransparent(false);

        this.getChildren().add(mawerLayer);
        this.getChildren().add(plantLayer);
        this.getChildren().add(zombieLayer);
        this.getChildren().add(bulletLayer);

    }

    public void update() {
        plantLayer.update();
        zombieLayer.update();
        bulletLayer.update();
        mawerLayer.update();
    }
}
