package fr.tanchou.pvz.gui.props;

import fr.tanchou.pvz.game.rowComponent.PlantCase;
import javafx.scene.layout.Pane;

public class CellView extends Pane {
    private final PlantCase plantCase;

    public CellView(double width, double height, PlantCase plantCase) {
        super();
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.plantCase = plantCase;
        this.setPrefSize(width, height);
        this.setStyle("-fx-border-color: rgba(246,0,0,0.76); -fx-border-width: 1; -fx-background-color: rgba(6,82,255,0.53);"); // Bordure pour visualiser les cellules
        this.setOpacity(0.999);
    }

    public void update() {
        if (plantCase.getPlant() != null && this.getOpacity() < 1) {

                EntityView entityView = new EntityView(plantCase.getPlant(), 150, 150);
                entityView.setMouseTransparent(true);
                this.getChildren().add(entityView);
                System.out.println("Plant added");
                setOpacity(1);

        }else {
            if (!this.getChildren().isEmpty() && this.getOpacity() == 1) {
                //this.getChildren().clear();
                //System.out.println("Plant removed");
            }
        }
    }

    public PlantCase getPlantCase(){
        return this.plantCase;
    }
}
