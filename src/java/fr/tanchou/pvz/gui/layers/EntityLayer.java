package fr.tanchou.pvz.gui.layers;

import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.Bullet;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.PeaBullet;
import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.gui.props.BulletView;
import javafx.scene.layout.Pane;

public class EntityLayer extends Pane {
    private final PlantLayer plantLayer;
    private final ZombieLayer zombieLayer;
    private final BulletLayer bulletLayer;

    public EntityLayer(int width, int height, Partie partie) {
        super();
        //0.771w 0.919h
        // decalage x 0.1739 par rapprt a la largeur du panel parent
        // decalage y 0.0574 par rapport a la hauteur du panel parent

        this.setPrefSize(width*(0.771+0.1739), height*0.84);
        this.setLayoutX(width*0.1739);
        this.setLayoutY(height*0.11);

        this.setStyle("-fx-background-color: rgba(0,0,0,0);");

        this.plantLayer = new PlantLayer(width, height, partie);
        this.zombieLayer = new ZombieLayer(this.getPrefWidth(), this.getPrefHeight(), partie.getRows());
        this.bulletLayer = new BulletLayer(this.getPrefWidth(), this.getPrefHeight(), partie.getRows());

        this.getChildren().add(plantLayer);
        this.getChildren().add(zombieLayer);
        this.getChildren().add(bulletLayer);
        bulletLayer.toFront();
    }

    public void update() {
        plantLayer.update();
        zombieLayer.update();
        bulletLayer.update();
    }
}
