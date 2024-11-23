package fr.tanchou.pvz.entities.plants.passive.sunflower;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.EntitieVue;
import javafx.scene.layout.Pane;

public class Sun extends Bullet {
    private int health = 600;
    private boolean collected;
    private final int value;

    public Sun(Double x, int y, int damage, int speed) {
        super(damage, speed, x, y);
        this.value = 50;
        this.collected = false;
    }

    @Override
    public EntitieVue createVue(Pane parent) {
        return new SunVue(this);
    }

    public void tick() {
        health--;
    }

    public int getHealth() {
        return health;
    }

    public boolean isCollected() {
        return collected;
    }

    public int getValue() {
        return value;
    }

    public void collect() {
        this.collected = true;
    }
}
