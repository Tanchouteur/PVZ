package fr.tanchou.pvz.gui.controller;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.gui.layers.game.CellView;
import javafx.event.Event;
import javafx.event.EventHandler;

public class CellGridController implements EventHandler {
    private final Player player;
    private final CellView cellView;

    CellGridController(Player player, CellView  cellView){
        this.player = player;
        this.cellView = cellView;
    }

    @Override
    public void handle(Event event) {

    }
}
