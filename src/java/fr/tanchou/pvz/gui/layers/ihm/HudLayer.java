package fr.tanchou.pvz.gui.layers.ihm;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.entityRealisation.plants.PlantCard;
import fr.tanchou.pvz.gui.controller.PlayerCardController;
import fr.tanchou.pvz.gui.layers.game.props.PlantCardView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.Objects;

public class HudLayer extends Pane {
    private final LinkedList<PlantCardView> listPlantCardView = new LinkedList<>();

    public HudLayer(int width, int height, Player player) {
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

        int i = 0;
        for (PlantCard plantCard : player.getPlantCardsArray()){
            PlantCardView plantCardView = new PlantCardView(plantCard, i);
            plantCardView.setOnMouseClicked(new PlayerCardController(player, plantCardView));
            listPlantCardView.add(plantCardView);
            i++;
        }
    }

    public void update() {
        for (PlantCardView plantCardView : listPlantCardView){
            plantCardView.update();
        }
    }
}