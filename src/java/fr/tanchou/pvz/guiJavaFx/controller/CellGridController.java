package fr.tanchou.pvz.guiJavaFx.controller;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.guiJavaFx.props.CellView;
import javafx.event.Event;
import javafx.event.EventHandler;

public class CellGridController implements EventHandler {
    private final Player player;
    private final CellView cellView;

    public CellGridController(Player player, CellView cellView) {
        this.player = player;
        this.cellView = cellView;
    }

    @Override
    public void handle(Event event) {
        cellView.getChildren().removeIf(node -> node.getId() != null && node.getId().equals("hoverPreview"));

        player.buyPlant(cellView.getPlantCase().getX(), cellView.getPlantCase().getY());

        // Débogage
        //System.err.println("cell clicked x = " + cellView.getPlantCase().getX() + " - y = " + cellView.getPlantCase().getY());
    }
}
