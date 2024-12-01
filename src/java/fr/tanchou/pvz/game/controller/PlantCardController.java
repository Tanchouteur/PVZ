package fr.tanchou.pvz.game.controller;

import fr.tanchou.pvz.entities.plants.Plant;
import fr.tanchou.pvz.entities.plants.PlantCard;
import fr.tanchou.pvz.entities.plants.VuePlantCard;
import fr.tanchou.pvz.player.Player;
import fr.tanchou.pvz.player.gui.HudPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class PlantCardController implements EventHandler<MouseEvent> {
    private final Player player;
    private final HudPane hudPane;

    // Constructeur qui prend en paramètre le modèle de case et le joueur
    public PlantCardController(HudPane hudPane) {
        this.hudPane = hudPane;
        this.player = hudPane.getPlayer();
    }

    // Méthode appelée lors du clic
    @Override
    public void handle(MouseEvent mouseEvent) {
        if (!(mouseEvent.getSource() instanceof VuePlantCard vuePlantCard)) {
            return;
        }
        PlantCard plantCard = vuePlantCard.getPlantCard();

        if (player.getSelectedPlant()==null) {
            Plant plant = plantCard.getPlant(); // Crée une nouvelle plante avec la carte actuelle
            hudPane.createPlantUnderMouse(plant, mouseEvent.getSceneX(), mouseEvent.getSceneY());
        }else {
            hudPane.getAnimationLayer().getChildren().remove(player.getSelectedPlant().getVue().getImageView());
            player.setSelectedPlant(null);
        }
        System.out.println("Clicked on " + plantCard.getName());
    }
}
