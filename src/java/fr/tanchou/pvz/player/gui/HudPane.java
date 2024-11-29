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
    private Plant activePlant = null; // Stocke la plante active (sous la souris)
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

                        System.out.println("Clicked on " + plantCard.getName());

                        if (player.getSelectedPlant()==null) {
                            Plant plant = plantCard.getPlant(); // Crée une nouvelle plante avec la carte actuelle
                            createPlantUnderMouse(plant, mouseEvent.getSceneX(), mouseEvent.getSceneY());
                        }else {
                            player.setSelectedPlant(null);
                            anim.getChildren().remove(activePlant.getVue().getImageView());
                            activePlant.setVue(null);
                            activePlant = null;
                        }
                    }
                });
                plantPane.getChildren().add(vuePlantCard); // Ajout à VBox
            }
        }

        return plantPane;
    }

    public void createPlantUnderMouse(Plant plant, double mouseX, double mouseY) {



        if (activePlant != null && activePlant == plant) {
            return; // Une plante est déjà active
        }
        //System.out.println("Plant : " + plant + " activePlant : " + activePlant);

        // Initialiser la plante active
        player.setSelectedPlant(plant);
        activePlant = plant;
        isPlantFollowingMouse = true;

        plant.setVue(plant.createVue(anim));
        activePlant.getVue().getImageView().setFitHeight(100);
        activePlant.getVue().getImageView().setFitWidth(100);

        anim.getChildren().add(plant.getVue().getImageView());

        // Position initiale de la plante sous la souris
        updatePlantPosition(mouseX, mouseY);
    }

    private void updatePlantPosition(double mouseX, double mouseY) {
        if (activePlant != null) {
            mouseX -= anim.getLayoutX();
            mouseY -= anim.getLayoutY();

            activePlant.getVue().getImageView().setLayoutX(mouseX - activePlant.getVue().getImageView().getFitWidth() / 2);
            activePlant.getVue().getImageView().setLayoutY(mouseY - activePlant.getVue().getImageView().getFitHeight() / 2);
        }
    }

    private void placePlant(double mouseX, double mouseY) {
        if (activePlant != null) {
            // Poser la plante à la position finale
            activePlant.getVue().getImageView().setLayoutX(mouseX - activePlant.getVue().getImageView().getFitWidth() / 2);
            activePlant.getVue().getImageView().setLayoutY(mouseY - activePlant.getVue().getImageView().getFitHeight() / 2);

            // Désactiver le suivi
            activePlant = null;
            isPlantFollowingMouse = false;
        }
    }

    public void update(double mouseX, double mouseY) {
        // Mise à jour de la position de la plante si elle suit la souris
        if (isPlantFollowingMouse && activePlant != null) {
            updatePlantPosition(mouseX, mouseY);
        }
    }
}
