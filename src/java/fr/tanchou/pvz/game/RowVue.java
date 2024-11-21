package fr.tanchou.pvz.game;

import fr.tanchou.pvz.entities.BulletVue;
import fr.tanchou.pvz.entities.Entitie;
import fr.tanchou.pvz.entities.EntitieVue;
import fr.tanchou.pvz.entities.plants.Plant;
import fr.tanchou.pvz.entities.plants.PlantVue;
import fr.tanchou.pvz.entities.zombie.Zombie;
import fr.tanchou.pvz.entities.zombie.ZombieVue;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class RowVue {
    private Row row;

    private final Pane plantLayer;
    private final Pane zombieLayer;
    private final Pane bulletLayer;

    private List<PlantVue> plantVues;
    private List<ZombieVue> zombieVues;
    private List<BulletVue> bulletVues;

    public RowVue(Row row) {
        this.row = row;

        plantLayer = new Pane();
        zombieLayer = new Pane();
        bulletLayer = new Pane();

        plantVues = new ArrayList<>();
        zombieVues = new ArrayList<>();
        bulletVues = new ArrayList<>();

        for (Plant plant : row.getListPlants()) {
            EntitieVue plantVue = plant.createVue();
            plantVues.add((PlantVue) plantVue);
            plantLayer.getChildren().add(plantVue.getImageView());
        }

        for (Zombie zombie : row.getListZombies()) {
            EntitieVue zombieVue = zombie.createVue();
            zombieVues.add((ZombieVue) zombieVue);
            zombieLayer.getChildren().add(zombieVue.getImageView());
        }

        for (Entitie bullet : row.getListBullets()) {
            EntitieVue bulletVue = bullet.createVue();
            bulletVues.add((BulletVue) bulletVue);
            bulletLayer.getChildren().add(bulletVue.getImageView());
        }
    }

    public void update() {
        // Met à jour la position de chaque vue d'entité
        for (PlantVue plantVue : plantVues) {
            plantVue.update();
        }

        for (ZombieVue zombieVue : zombieVues) {
            zombieVue.update();
        }

        for (BulletVue bulletVue : bulletVues) {
            bulletVue.update();
        }
    }

    public Pane getPlantLayer() {
        return plantLayer;
    }
}

