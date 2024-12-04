package fr.tanchou.pvz.gui.layers.game;

import fr.tanchou.pvz.game.rowComponent.PlantCase;
import fr.tanchou.pvz.gui.layers.game.props.EntityView;
import javafx.scene.layout.Pane;

public class CellView extends Pane {
    private final PlantCase plantCase;

    public CellView(PlantCase plantCase) {
        super();

        this.setStyle("-fx-background-color: rgba(36,255,6,0.34);");

        this.plantCase = plantCase;
    }

    public void update() {
        if (plantCase.getPlant() != null) {
            if (this.getChildren().isEmpty()) {
                this.getChildren().add(new EntityView(plantCase.getPlant(), this.getWidth()-(this.getWidth()/4), this.getHeight() - (this.getHeight()/4)));
            }
        }else {
            this.getChildren().clear();
        }
    }
}
