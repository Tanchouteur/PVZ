package fr.tanchou.pvz.guiJavaFx.controller;

import fr.tanchou.pvz.game.Player;
import fr.tanchou.pvz.guiJavaFx.props.CellView;
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
        cellView.getChildren().removeIf(node -> node.getId() != null && node.getId().equals("hoverPreview"));

        if (player.getActivPlantCard() != null && cellView.getPlantCase().isEmpty()) {

            Image image = cellView.getAssetsLoader().getAssetEntity(player.getActivPlantCard().getPlant()).get("normal");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setMouseTransparent(true);
            imageView.setId("hoverPreview");
            cellView.getChildren().add(imageView);
            cellView.setHovered(true);

        }else if (player.canShovel() && !cellView.getPlantCase().isEmpty()) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/items/shovel.png")));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setMouseTransparent(true);
            imageView.setId("shovelPreview");
            cellView.getChildren().add(imageView);
            cellView.setHovered(true);


        }else {
            cellView.setHovered(false);
            cellView.getChildren().removeIf(node -> node.getId() != null && node.getId().equals("shovelPreview"));
        }
    }
}