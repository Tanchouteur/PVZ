package fr.tanchou.pvz.gui.controller;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.gui.layers.game.props.PlantCardView;
import javafx.event.Event;
import javafx.event.EventHandler;

public class PlayerCardController implements EventHandler {
    private final Player player;
    private final PlantCardView plantCardView;

    public PlayerCardController(Player player, PlantCardView plantCardView) {
        this.player = player;
        this.plantCardView = plantCardView;
    }

    @Override
    public void handle(Event event) {
        player.preBuyPlant(plantCardView.getPlantCard());
    }
}
