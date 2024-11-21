package fr.tanchou.pvz.entities;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public abstract class EntitieVue {
    protected Entitie entitie;  // Référence à l'entité (abstraite)
    protected ImageView imageView;

    public EntitieVue(Entitie entitie, ImageView imageView) {
        this.entitie = entitie;
        this.imageView = imageView;
        entitie.createVue();
    }

    public void update() {
        // Mettre à jour la position de l'entité
        imageView.setLayoutX(entitie.getX());
        imageView.setLayoutY(entitie.getY());
    }

    public ImageView getImageView() {
        return imageView;
    }
}
