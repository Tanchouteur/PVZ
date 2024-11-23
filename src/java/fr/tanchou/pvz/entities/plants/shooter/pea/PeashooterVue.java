package fr.tanchou.pvz.entities.plants.shooter.pea;

import fr.tanchou.pvz.entities.plants.PlantVue;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;

import static java.lang.System.exit;

public class PeashooterVue extends PlantVue {

    public PeashooterVue(PeaShooter plant, Pane parent) {

        URL url = plant.getClass().getResource("/assets/plants/peaShooter/Peashooteringamerender.png");
        if (url == null) {
            System.err.println("Impossible de charger l'image du Peashooter");
            exit(1);
        }

        super(plant, new ImageView(url.toString()), parent);
    }
}