package fr.tanchou.pvz.entities.plants;

import fr.tanchou.pvz.entities.EntitieVue;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class PlantVue extends EntitieVue {

    public PlantVue(Plant plant, ImageView imageView, Pane parent) {
        super(plant, imageView);
    }

    @Override
    public void updateDetails(Pane parent) {
        imageView.setFitWidth(100); // Largeur fixe
        imageView.setFitHeight(100); // Largeur fixe

        imageView.setLayoutY(50);
    }

}
