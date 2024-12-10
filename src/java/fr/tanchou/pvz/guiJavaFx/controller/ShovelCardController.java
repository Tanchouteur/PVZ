package fr.tanchou.pvz.guiJavaFx.controller;

import fr.tanchou.pvz.Player;
import javafx.event.Event;
import javafx.event.EventHandler;

public class ShovelCardController implements EventHandler {
    private final Player player;

    public ShovelCardController(Player player) {
        this.player = player;
    }

    @Override
    public void handle(Event event) {
        player.toggleShovel();
    }
}