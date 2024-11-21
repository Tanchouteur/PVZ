package fr.tanchou.pvz.game;

import fr.tanchou.pvz.entities.Entitie;
import fr.tanchou.pvz.entities.EntitieVue;
import fr.tanchou.pvz.entities.plants.Plant;
import fr.tanchou.pvz.entities.zombie.Zombie;
import javafx.scene.layout.Pane;

public class RowVue {

    private final Pane plantLayer;
    private final Pane zombieLayer;
    private final Pane bulletLayer;
    private final Pane rowPane;  // Nouveau Pane parent qui contient les trois couches
    private final Row row;

    public RowVue(Row row) {
        this.row = row;
        plantLayer = new Pane();
        zombieLayer = new Pane();
        bulletLayer = new Pane();
        rowPane = new Pane();  // Initialisation du Pane parent

        // Ajout des couches au Pane parent (rowPane)
        rowPane.getChildren().addAll(plantLayer, zombieLayer, bulletLayer);

        // Ajouter les plantes à la couche correspondante
        for (Plant plant : row.getListPlants()) {
            EntitieVue plantVue = plant.createVue();
            plantLayer.getChildren().add(plantVue.getImageView());
        }

        // Ajouter les zombies à la couche correspondante
        for (Zombie zombie : row.getListZombies()) {
            EntitieVue zombieVue = zombie.createVue();
            zombieLayer.getChildren().add(zombieVue.getImageView());
        }

        // Ajouter les projectiles à la couche correspondante
        for (Entitie bullet : row.getListBullets()) {
            EntitieVue bulletVue = bullet.createVue();
            bulletLayer.getChildren().add(bulletVue.getImageView());
        }
    }

    public void update() {
        // Met à jour la position et l'état des plantes
        for (Plant plant : row.getListPlants()) {
            if (!plantLayer.getChildren().contains(plant.getVue().getImageView())) {
                plantLayer.getChildren().add(plant.getVue().getImageView());
            }
            plant.getVue().update();
        }

        // Met à jour la position et l'état des zombies
        for (Zombie zombie : row.getListZombies()) {
            if (!zombieLayer.getChildren().contains(zombie.getVue().getImageView())) {
                zombieLayer.getChildren().add(zombie.getVue().getImageView());
            }
            zombie.getVue().update();
        }

        // Met à jour la position et l'état des projectiles
        for (Entitie bullet : row.getListBullets()) {
            if (!bulletLayer.getChildren().contains(bullet.getVue().getImageView())) {
                bulletLayer.getChildren().add(bullet.getVue().getImageView());
            }
            bullet.getVue().update();
        }
    }

    public Pane getRowPane() {
        return rowPane;  // Retourne le Pane parent qui contient toutes les couches
    }
}