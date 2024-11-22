package fr.tanchou.pvz.entities.zombie;

import fr.tanchou.pvz.entities.Entitie;
import fr.tanchou.pvz.entities.EntitieVue;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;

public class ZombieVue extends EntitieVue {

    public ZombieVue(Entitie entitie, URL url, Pane parent) {
        ImageView imageView = new ImageView(url.toString());

        imageView.setFitWidth(75);
        imageView.setFitHeight(100);

        double bottomPosition = parent.getHeight() - imageView.getFitHeight(); // Hauteur totale du parent moins la hauteur de l'image
        imageView.setLayoutY(bottomPosition);

        super(entitie, imageView);
    }

    @Override
    public void updateDetails(Pane parent) {
        imageView.setFitWidth(100*1.5);
        imageView.setFitHeight(150*1.5);

        double bottomPosition = parent.getHeight() - (imageView.getFitHeight()+50); // Hauteur totale du parent moins la hauteur de l'image
        imageView.setLayoutY(bottomPosition);
    }
}