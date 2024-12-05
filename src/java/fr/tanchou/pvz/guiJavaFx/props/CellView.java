package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.game.rowComponent.PlantCase;
import javafx.scene.layout.Pane;

public class CellView extends Pane {
    private final PlantCase plantCase;
    private boolean isHovered = false;

    public CellView(double width, double height, PlantCase plantCase) {
        super();
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.plantCase = plantCase;
        this.setPrefSize(width, height);
    }

    public void update() {
        // Gère l'ajout d'une plante
        if (!plantCase.isEmpty() && this.getChildren().stream().noneMatch(node -> node instanceof EntityView)) {
            PlantView entityView = new PlantView(plantCase.getPlant(), 150, 150);
            entityView.setMouseTransparent(true);
            this.getChildren().add(entityView);
            System.out.println("Plant added");
            setOpacity(1);
            setHovered(false);
        }

        // Supprime la vue de la plante si la case est vide (mais pas la prévisualisation)
        if (plantCase.isEmpty() && this.getChildren().stream().anyMatch(node -> node instanceof EntityView)) {
            this.getChildren().removeIf(node -> node instanceof EntityView);
            System.out.println("Plant removed");
        }

        // Gere la transparence pour l'effet hover
        if (isHovered) {
            setOpacity(0.7);
        } else {
            setOpacity(1);
        }
    }

    public PlantCase getPlantCase(){
        return this.plantCase;
    }

    public void setHovered(boolean hovered) {
        isHovered = hovered;
    }
}
