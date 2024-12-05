package fr.tanchou.pvz.gui.controller;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.gui.props.CellView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;


public class HoverCellController implements EventHandler {
    private final Player player;
    private final CellView cellView;

    public HoverCellController(Player player, CellView cellView){
        this.player = player;
        this.cellView = cellView;
    }

    @Override
    public void handle(Event event) {
        if (player.getActivPlantCard() != null && cellView.getPlantCase().isEmpty()) {
            Image image = new Image(Objects.requireNonNull(player.getClass().getResourceAsStream("/assets/plants/"+ player.getActivPlantCard().getPlant().getName() +".gif")));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setMouseTransparent(true);
            imageView.setLayoutX(0);
            imageView.setLayoutY(0);
            cellView.getChildren().add(imageView);
            cellView.setHovered(true);
            cellView.setOpacity(0.8);
            //System.err.println("cell hovered x = " + cellView.getPlantCase().getX() + " - y = " + cellView.getPlantCase().getY());
        }

    }
}