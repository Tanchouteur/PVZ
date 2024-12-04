package fr.tanchou.pvz.gui.controller;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.gui.props.CellView;
import javafx.event.Event;
import javafx.event.EventHandler;

public class CellGridController implements EventHandler {
    private final Player player;
    private final CellView cellView;

    public CellGridController(Player player, CellView cellView){
        this.player = player;
        this.cellView = cellView;
    }

    @Override
    public void handle(Event event) {
        System.err.println("cell cliqued x = " + cellView.getPlantCase().getX() + " - y = " + cellView.getPlantCase().getY());

        player.buyPlant(cellView.getPlantCase().getX(), cellView.getPlantCase().getY());
        cellView.setOpacity(1);
    }
}
