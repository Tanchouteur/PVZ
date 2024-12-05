package fr.tanchou.pvz.guiJavaFx.controller;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.guiJavaFx.props.CellView;
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
        cellView.setOpacity(1);
        player.buyPlant(cellView.getPlantCase().getX(), cellView.getPlantCase().getY());


        System.err.println("cell cliqued x = " + cellView.getPlantCase().getX() + " - y = " + cellView.getPlantCase().getY());
    }
}