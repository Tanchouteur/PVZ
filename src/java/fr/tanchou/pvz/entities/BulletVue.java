package fr.tanchou.pvz.entities;

import javafx.scene.image.ImageView;

public class BulletVue extends EntitieVue {

    public BulletVue(Bullet bullet, ImageView imageView) {
        imageView.setFitWidth(25); // Largeur fixe
        imageView.setFitHeight(25);
        super(bullet, imageView);
    }
}
