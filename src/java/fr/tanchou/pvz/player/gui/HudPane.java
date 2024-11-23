package fr.tanchou.pvz.player.gui;

import fr.tanchou.pvz.player.Player;
import javafx.scene.layout.Pane;

public class HudPane extends Pane {

    private Player player;
    private Pane plantPane;

    public HudPane(Player player) {
        this.setPrefSize(200, 600);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        // Ajout des éléments graphiques
        this.player = player;
        this.plantPane = createPlantPane();
    }

    private Pane createPlantPane() {
        Pane plantPane = new Pane();
        plantPane.setPrefSize(200, 500);

        // Ajout des éléments graphiques

        for (int i = 0; i < player.getPlantCards().length; i++) {
            //player.getPlantCards()[i].createVue();
        }

        return plantPane;
    }

    public void update() {
        // Mise à jour des éléments graphiques
    }


}
