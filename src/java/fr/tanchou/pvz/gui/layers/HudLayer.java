package fr.tanchou.pvz.gui.layers;

import fr.tanchou.pvz.PlayerController;
import fr.tanchou.pvz.game.Partie;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class HudLayer extends Pane {
    public HudLayer(int width, int height, Partie partie) {
        super();
        //0.146w 0.56h
        this.setPrefSize(width*0.146, height*0.56);
        this.setLayoutX(width*0.00625);
        this.setLayoutY(((double) height /2) - this.getPrefHeight()/2);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player/panelCard.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(this.getPrefWidth());
        imageView.setFitHeight(this.getPrefHeight());
        this.getChildren().add(imageView);
    }

    public void update(PlayerController player) {
    }
}
