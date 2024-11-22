package fr.tanchou.pvz.entities.plants;

import fr.tanchou.pvz.entities.EntitieVue;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class PlantVue extends EntitieVue {

    public PlantVue(Plant plant, ImageView imageView, Pane parent) {

        imageView.setFitWidth(50); // Largeur fixe
        imageView.setFitHeight(50); // Largeur fixe

        double bottomPosition = 100.0 - 50.0; // Hauteur totale du parent moins la hauteur de l'image
        imageView.setLayoutY(50);

        super(plant, imageView);
    }

}
