package fr.tanchou.pvz.player.gui;

import fr.tanchou.pvz.entityRealisation.plants.PlantCard;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ControllerPlantCard implements EventHandler<MouseEvent> {

    private Pane gamePane; // Le Pane où les plantes sont placées (ex : le terrain de jeu)

    public ControllerPlantCard(Pane gamePane) {
        this.gamePane = gamePane;
    }

    public VuePlantCard createVue(PlantCard plantCard) {
        VuePlantCard vuePlantCard = new VuePlantCard(plantCard);

        vuePlantCard.setFitHeight(100);
        vuePlantCard.setFitWidth(100);
        return vuePlantCard;
    }



    @Override
    public void handle(MouseEvent mouseEvent) {
         // Place la plante sous la souris
    }
}
