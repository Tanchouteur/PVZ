package fr.tanchou.pvz;

import javafx.scene.image.ImageView;

public class PlanteVue {
    private Plant plant;
    private ImageView imageView;

    public PlanteVue(Plant plant) {
        this.plant = plant;
        this.imageView = new ImageView("chemin/vers/image_plante.png");
        imageView.setLayoutX(plant.getPosition());
    }

    public void update() {
        // Mettre à jour la position ou l'état graphique si nécessaire
    }
}
