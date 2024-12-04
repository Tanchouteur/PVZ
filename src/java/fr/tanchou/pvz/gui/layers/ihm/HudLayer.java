package fr.tanchou.pvz.gui.layers.ihm;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.entityRealisation.plants.PlantCard;
import fr.tanchou.pvz.gui.controller.PlayerCardController;
import fr.tanchou.pvz.gui.props.PlantCardView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.Objects;

public class HudLayer extends Pane {
    private final LinkedList<PlantCardView> listPlantCardView = new LinkedList<>();
    private final SoldView soldView;

    public HudLayer( Player player) {
        super();
        //0.146w 0.56h
        /*this.setPrefSize(width*0.1, height*0.56);
        this.setLayoutX(width*0.00625);
        this.setLayoutY(((double) height /2) - this.getPrefHeight()/2);*/



        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/player/panelCard.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(this.getPrefHeight());
        imageView.setOpacity(0.5);
        imageView.setMouseTransparent(true);
        this.getChildren().add(imageView);

        this.soldView = new SoldView(400, 400, player);
        this.soldView.setLayoutX(10);
        this.getChildren().add(soldView);

        int i = 0;
        for (PlantCard plantCard : player.getPlantCardsArray()){
            PlantCardView plantCardView = new PlantCardView(plantCard, i);
            plantCardView.setOnMouseClicked(new PlayerCardController(player, plantCardView));
            listPlantCardView.add(plantCardView);
            this.getChildren().add(plantCardView);
            plantCardView.setMouseTransparent(false);
            i++;
        }
    }

    public void update() {
        soldView.update();
        for (PlantCardView plantCardView : listPlantCardView){
            plantCardView.update();
        }
    }
}