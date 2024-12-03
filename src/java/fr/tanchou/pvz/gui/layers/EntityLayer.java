package fr.tanchou.pvz.gui.layers;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.rowComponent.Row;
import javafx.scene.layout.Pane;

public class EntityLayer extends Pane {
    private PlantLayer plantLayer;
    private ZombieLayer zombieLayer;

    public EntityLayer(int width, int height, Partie partie) {
        super();
        //0.771w 0.919h
        // decalage x 0.1739 par rapprt a la largeur du panel parent
        // decalage y 0.0574 par rapport a la hauteur du panel parent

        this.setPrefSize(width*(0.771+0.1739), height*0.84);
        this.setLayoutX(width*0.1739);
        this.setLayoutY(height*0.11);

        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.30);");

        this.plantLayer = new PlantLayer(width, height, partie);
        this.zombieLayer = new ZombieLayer(this.getWidth(), this.getHeight());

        this.getChildren().add(plantLayer);
        this.getChildren().add(zombieLayer);
    }

    public void update() {
        plantLayer.update();
        zombieLayer.update();
    }
}
