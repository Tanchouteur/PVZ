package fr.tanchou.pvz.guiJavaFx.props;

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
        //this.setStyle("-fx-border-color: rgba(246,0,0,0.76); -fx-border-width: 1; -fx-background-color: rgba(6,82,255,0.53);"); // Bordure pour visualiser les cellules
    }

    public void update() {
        //si la case n'est pas vide et que le panel n'est pas déjà rempli

        if (!plantCase.isEmpty() && this.getChildren().isEmpty()) {

                EntityView entityView = new EntityView(plantCase.getPlant(), 150, 150);
                entityView.setMouseTransparent(true);
                this.getChildren().add(entityView);
                System.out.println("Plant added");
                setOpacity(1);
                setHovered(false);

        }else if (plantCase.isEmpty() && !isHovered && !this.getChildren().isEmpty()) {

                this.getChildren().clear();
                System.out.println("Plant removed");

        }
    }

    public PlantCase getPlantCase(){
        return this.plantCase;
    }

    public void setHovered(boolean hovered) {
        isHovered = hovered;
    }
}
