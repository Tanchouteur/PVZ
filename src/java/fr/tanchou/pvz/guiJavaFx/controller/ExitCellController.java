package fr.tanchou.pvz.guiJavaFx.controller;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.guiJavaFx.props.CellView;
import javafx.event.Event;
import javafx.event.EventHandler;

public class ExitCellController implements EventHandler {
    private final Player player;
    private final CellView cellView;

    public ExitCellController(Player player, CellView cellView) {
        this.player = player;
        this.cellView = cellView;
    }

    @Override
    public void handle(Event event) {
        if (player.getActivPlantCard() != null && cellView.getPlantCase().isEmpty()) {

            cellView.getChildren().removeIf(node -> node.getId() != null && node.getId().equals("hoverPreview"));
            cellView.setHovered(false);
        }
        // déboguer
        // System.err.println("cell exited x = " + cellView.getPlantCase().getX() + " - y = " + cellView.getPlantCase().getY());
    }
}