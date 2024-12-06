package fr.tanchou.pvz.guiJavaFx.controller;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.guiJavaFx.props.PlantCardView;
import javafx.event.Event;
import javafx.event.EventHandler;

public class PlayerCardController implements EventHandler {
    private final Player player;
    private final PlantCardView plantCardView;

    public PlayerCardController(Player player, PlantCardView plantCardView) {
        this.player = player;
        this.plantCardView = plantCardView;
        plantCardView.setMouseTransparent(false);
    }

    @Override
    public void handle(Event event) {
        player.preBuyPlant(plantCardView.getPlantCard());
    }
}
