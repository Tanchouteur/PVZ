package fr.tanchou.pvz.guiJavaFx.controller;

import fr.tanchou.pvz.Player;
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
        // Supprime toute prévisualisation existante pour éviter des doublons
        cellView.getChildren().removeIf(node -> node.getId() != null && node.getId().equals("hoverPreview"));

        // Ajoute une prévisualisation si la case est vide et une plante active est sélectionnée
        if (player.getActivPlantCard() != null && cellView.getPlantCase().isEmpty()) {
            System.err.println("/assets/plants/" + player.getActivPlantCard().getPlant().getName() + ".gif");
            Image image = new Image(Objects.requireNonNull(player.getClass().getResourceAsStream(
                    "/assets/plants/" + player.getActivPlantCard().getPlant().getName() + ".gif")));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setFitHeight(150);
            imageView.setMouseTransparent(true);
            imageView.setId("hoverPreview"); // Identifiant pour distinguer les prévisualisations
            cellView.getChildren().add(imageView);
            cellView.setHovered(true);
        }
    }
}