package fr.tanchou.pvz.entities.plants;

import fr.tanchou.pvz.entities.EntitieVue;
import javafx.scene.image.ImageView;

public abstract class PlantVue extends EntitieVue {

    public PlantVue(Plant plant, ImageView imageView) {
        super(plant, imageView);
    }

}
