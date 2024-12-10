package fr.tanchou.pvz.guiJavaFx.controller;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.guiJavaFx.props.CellView;
import fr.tanchou.pvz.guiJavaFx.sound.SoundManager;
import javafx.event.Event;
import javafx.event.EventHandler;

public class CellGridController implements EventHandler {
    private final Player player;
    private final CellView cellView;
    private final SoundManager soundManager;

    public CellGridController(Player player, CellView cellView, SoundManager soundManager) {
        this.player = player;
        this.cellView = cellView;
        this.soundManager = soundManager;
    }

    @Override
    public void handle(Event event) {
        cellView.getChildren().removeIf(node -> node.getId() != null && node.getId().equals("hoverPreview"));

        if (player.buyPlant(cellView.getPlantCase().getX(), cellView.getPlantCase().getY())){
            soundManager.playSound("plant");
        }else if (player.canShovel() && !cellView.getPlantCase().isEmpty()) {
            player.shovelPlant(cellView.getPlantCase().getX(), cellView.getPlantCase().getY());
            soundManager.playSound("shovel");
            cellView.getChildren().removeIf(node -> node.getId() != null && node.getId().equals("shovelPreview"));
        }

        // Débogage
        //System.err.println("cell clicked x = " + cellView.getPlantCase().getX() + " - y = " + cellView.getPlantCase().getY());
    }
}
