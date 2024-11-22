package fr.tanchou.pvz.entities;

import javafx.scene.image.ImageView;

public abstract class EntitieVue {
    protected Entitie entitie;  // Référence à l'entité (abstraite)
    protected ImageView imageView;

    public EntitieVue(Entitie entitie, ImageView imageView) {
        this.entitie = entitie;
        this.imageView = imageView;
    }

    public void update() {
        // Mettre à jour la position de l'entité
        imageView.setLayoutX(entitie.getX()*100);
        imageView.setLayoutY(entitie.getY()*100);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
