package fr.tanchou.pvz.entities.plants.passive.sunflower;

import fr.tanchou.pvz.entities.BulletVue;
import javafx.scene.image.ImageView;

import java.net.URL;

import static java.lang.System.exit;

public class SunVue extends BulletVue {
    SunVue(Sun sun) {
        URL url = sun.getClass().getResource("/assets/items/Sun/SunAnimated.gif");
        if (url == null) {
            System.err.println("Impossible de charger l'image du Sun");
            exit(1);
        }
        ImageView imageView = new ImageView(url.toString());
        imageView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            sun.collect();
        });
        super(sun, new ImageView(url.toString()));

        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
    }
}
