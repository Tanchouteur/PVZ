package fr.tanchou.pvz.gui.layers;

import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import fr.tanchou.pvz.game.SunManager;
import fr.tanchou.pvz.gui.props.SunView;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class SunLayer extends Pane {
    private final SunManager sunManager;

    public SunLayer(int width, int height, SunManager sunManager) {
        super();
        this.setPrefSize(width, height);
        this.setLayoutX(0);
        this.setLayoutY(0);

        this.sunManager = sunManager;
    }

    public void update() {
        // Liste des noeuds à supprimer
        List<Node> nodesToRemove = new ArrayList<>();

        // Identifier les vues obsolètes (pas dans la liste du SunManager)
        for (Node node : this.getChildren()) {
            if (node instanceof SunView sunView) {
                if (!sunManager.getSunLinkedList().contains(sunView.getSun())) {
                    nodesToRemove.add(sunView);
                }
            }
        }

        // Supprimer les vues obsolètes
        //this.getChildren().removeAll(nodesToRemove);

        // Identifier les soleils sans vue
        for (Sun sun : sunManager.getSunLinkedList()) {
            boolean exists = false;
            for (Node node : this.getChildren()) {
                if (node instanceof SunView) {
                    SunView sunView = (SunView) node;
                    if (sunView.getSun() == sun) {
                        exists = true;
                        break;
                    }
                }
            }

            // Ajouter une vue pour le soleil s'il n'existe pas déjà
            if (!exists) {
                this.getChildren().add(new SunView(sun));
            }
        }

        // Mettre à jour la position des soleils affichés
        for (Node node : this.getChildren()) {
            if (node instanceof SunView) {
                SunView sunView = (SunView) node;
                sunView.updateSunPosition();
            }
        }
    }
}
