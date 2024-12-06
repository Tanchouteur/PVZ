package fr.tanchou.pvz.guiJavaFx.layers.game;

import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import fr.tanchou.pvz.game.SunManager;
import fr.tanchou.pvz.guiJavaFx.props.SunView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class SunLayer extends Pane {
    private final SunManager sunManager;
    private final Map<Sun, SunView> sunViewMap = new HashMap<>();
    private final Image sunImage;

    public SunLayer(int width, int height, SunManager sunManager, Image asset) {
        super();
        this.setPrefSize(width, height);
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.sunManager = sunManager;
        this.sunImage = asset;
    }

    public void update() {
        // Supprimer les vues des soleils qui n'existent plus dans le SunManager
        sunViewMap.entrySet().removeIf(entry -> {
            if (!sunManager.getSunLinkedList().contains(entry.getKey())) {
                this.getChildren().remove(entry.getValue());
                return true;
            }
            return false;
        });

        // Ajouter des vues pour les soleils qui n'en ont pas encore
        for (Sun sun : sunManager.getSunLinkedList()) {
            if (!sunViewMap.containsKey(sun)) {
                SunView sunView = new SunView(sun, sunImage);
                sunViewMap.put(sun, sunView);
                this.getChildren().add(sunView);
            }
        }

        // Mettre à jour toutes les positions des soleils
        /*for (SunView sunView : sunViewMap.values()) {
            sunView.updateSunPosition();
        }*/
    }
}
