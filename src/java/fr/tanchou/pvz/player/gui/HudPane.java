package fr.tanchou.pvz.player.gui;

import fr.tanchou.pvz.entities.plants.Plant;
import fr.tanchou.pvz.entities.plants.PlantCard;
import fr.tanchou.pvz.entities.plants.VuePlantCard;
import fr.tanchou.pvz.player.Player;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HudPane extends Pane {

    private Player player;
    private Pane plantPane;
    private Pane anim;

    private ControllerPlantCard controllerPlantCard;

    private boolean isPlantFollowingMouse = false; // Indique si une plante suit actuellement la souris

    public HudPane(Player player, Pane animationLayer) {
        this.setPrefSize(200, 600);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        // Initialisation des éléments graphiques
        this.player = player;
        this.plantPane = createPlantPane();
        this.controllerPlantCard = new ControllerPlantCard(animationLayer);
        this.getChildren().add(plantPane);
        this.anim = animationLayer;
    }

    private Pane createPlantPane() {
        VBox plantPane = new VBox(); // VBox pour un empilement vertical
        plantPane.setSpacing(5); // Espacement vertical entre les cartes
        plantPane.setLayoutX(0);
        plantPane.setLayoutY(0);
        plantPane.setPrefSize(200, 500);
        plantPane.setStyle("-fx-background-color: rgba(0,0,0,0);");

        // Ajout des cartes de plantes
        for (PlantCard plantCard : player.getPlantCards()) {
            if (plantCard != null) {
                VuePlantCard vuePlantCard = plantCard.createVue();

                vuePlantCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (!(mouseEvent.getSource() instanceof VuePlantCard vuePlantCard)) {
                            return;
                        }
                        PlantCard plantCard = vuePlantCard.getPlantCard();



                        if (player.getSelectedPlant()==null) {
                            Plant plant = plantCard.getPlant(); // Crée une nouvelle plante avec la carte actuelle
                            createPlantUnderMouse(plant, mouseEvent.getSceneX(), mouseEvent.getSceneY());
                        }else {
                            anim.getChildren().remove(player.getSelectedPlant().getVue().getImageView());
                            player.setSelectedPlant(null);
                        }
                        System.out.println("Clicked on " + plantCard.getName());
                    }
                });
                plantPane.getChildren().add(vuePlantCard); // Ajout à VBox
            }
        }

        return plantPane;
    }

    public void createPlantUnderMouse(Plant plant, double mouseX, double mouseY) {

        if (player.getSelectedPlant() != null && player.getSelectedPlant() == plant) {
            return; // Une plante est déjà active
        }
        //System.out.println("Plant : " + plant + " activePlant : " + activePlant);

        // Initialiser la plante active
        player.setSelectedPlant(plant);
        isPlantFollowingMouse = true;

        plant.setVue(plant.createVue(anim));
        player.getSelectedPlant().getVue().getImageView().setFitHeight(100);
        player.getSelectedPlant().getVue().getImageView().setFitWidth(100);

        anim.getChildren().add(plant.getVue().getImageView());

        // Position initiale de la plante sous la souris
        updatePlantPosition(mouseX, mouseY);
    }

    private void updatePlantPosition(double mouseX, double mouseY) {
        if (player.getSelectedPlant() != null) {
            mouseX -= anim.getLayoutX();
            mouseY -= anim.getLayoutY();

            player.getSelectedPlant().getVue().getImageView().setLayoutX(mouseX - player.getSelectedPlant().getVue().getImageView().getFitWidth() / 2);
            player.getSelectedPlant().getVue().getImageView().setLayoutY(mouseY - player.getSelectedPlant().getVue().getImageView().getFitHeight() / 2);
        }
    }

    public void update(double mouseX, double mouseY) {
        // Mise à jour de la position de la plante si elle suit la souris
        if (isPlantFollowingMouse && player.getSelectedPlant() != null) {
            updatePlantPosition(mouseX, mouseY);
        }
    }
}
