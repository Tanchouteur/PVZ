package fr.tanchou.pvz.entities;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class BulletVue extends EntitieVue {

    public BulletVue(Bullet bullet, ImageView imageView) {
        imageView.setFitWidth(25); // Largeur fixe
        imageView.setFitHeight(25);
        super(bullet, imageView);
    }

    @Override
    public void updateDetails(Pane parent) {
        imageView.setFitWidth(40); // Largeur fixe
        imageView.setFitHeight(40);

        imageView.setLayoutY(45);
    }
}
