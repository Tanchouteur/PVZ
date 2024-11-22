package fr.tanchou.pvz.entities;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class EntitieVue {
    protected Entitie entitie;  // Référence à l'entité (abstraite)
    protected ImageView imageView;

    public EntitieVue(Entitie entitie, ImageView imageView) {
        this.entitie = entitie;
        this.imageView = imageView;

    }

    public void update(Pane parent) {
        double bottomPosition = entitie.getY()*parent.getHeight() - imageView.getFitHeight(); // Hauteur totale du parent moins la hauteur de l'image

        // Mettre à jour la position de l'entité
        imageView.setLayoutX(entitie.getX()*100);
        imageView.setLayoutY(bottomPosition);

    }

    public ImageView getImageView() {
        return imageView;
    }
}
