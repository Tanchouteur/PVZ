package fr.tanchou.pvz.entities.plants;

import javafx.scene.image.ImageView;

public abstract class PlantVue {
    protected Plant plant;  // Référence à la plante (abstraite)
    protected ImageView imageView;

    public PlantVue(Plant plant) {
        this.plant = plant;
        this.imageView = new ImageView();
    }

    // Méthode abstraite qui sera implémentée dans chaque vue concrète
    protected abstract void updateImage();

    public ImageView getImageView() {
        return imageView;
    }

    public void update() {
        // Mettre à jour la position de la plante
        imageView.setLayoutX(plant.getX());
        imageView.setLayoutY(plant.getY());
    }
}
