package fr.tanchou.pvz.guiJavaFx.controller;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.guiJavaFx.props.PlantCardView;
import javafx.event.Event;
import javafx.event.EventHandler;

public class PlayerCardHoverController implements EventHandler {
    private final PlantCardView plantCardView;
    private final Player player;
    private boolean hoverEffect = false;

    public PlayerCardHoverController(PlantCardView plantCardView, Player player) {
        this.plantCardView = plantCardView;
        plantCardView.setMouseTransparent(false);
        this.player = player;
    }

    @Override
    public void handle(Event event) {
        if (plantCardView.getPlantCard().canBuy()){
            if (player.getActivPlantCard() != null && player.getActivPlantCard().getPlant() == plantCardView.getPlantCard().getPlant()){
                hoverEffect = false;
            }
            if (hoverEffect) {
                plantCardView.translateYProperty().set(+7);
                hoverEffect = false;
            }else {
                plantCardView.translateYProperty().set(-7);
                hoverEffect = true;
            }
        }
    }
}
