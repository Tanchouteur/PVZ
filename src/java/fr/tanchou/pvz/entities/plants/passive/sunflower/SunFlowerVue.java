package fr.tanchou.pvz.entities.plants.passive.sunflower;

import fr.tanchou.pvz.entities.plants.PlantVue;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;

import static java.lang.System.exit;

public class SunFlowerVue extends PlantVue {
    public SunFlowerVue(SunFlower plant, Pane parent) {
        URL url = plant.getClass().getResource("/assets/plants/sunFlower/SunflowerA.gif");
        if (url == null) {
            System.err.println("Impossible de charger l'image du sunflower");
            exit(1);
        }

        super(plant, new ImageView(url.toString()), parent);
    }
}
