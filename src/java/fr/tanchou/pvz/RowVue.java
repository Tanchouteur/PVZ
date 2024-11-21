package fr.tanchou.pvz;

import fr.tanchou.pvz.game.Row;
import javafx.scene.layout.Pane;

public class RowVue {
    private Row row;
    private Pane plantLayer;
    private Pane zombieLayer;
    private Pane bulletLayer;

    public RowVue(Row row) {
        this.row = row;
        plantLayer = new Pane();
        zombieLayer = new Pane();
        bulletLayer = new Pane();
    }

    public void update() {
        // Mettre à jour les plantes, zombies, et projectiles
    }

    public Pane getPlantLayer() {
        return plantLayer;
    }

    public Pane getZombieLayer() {
        return zombieLayer;
    }

    public Pane getBulletLayer() {
        return bulletLayer;
    }
}
