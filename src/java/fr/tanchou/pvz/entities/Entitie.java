package fr.tanchou.pvz.entities;

import javafx.scene.layout.Pane;

public abstract class Entitie {
    private Double x;
    private int y;
    private int healthPoint;
    private EntitieVue vue;

    public Entitie(int healthPoint, Double x, int y) {
        this.healthPoint = healthPoint;
        this.x = x;
        this.y = y;
        //this.vue = createVue();
    }

    public abstract EntitieVue createVue(Pane parent);

    public abstract void onDeath();

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public void  takeDamage(int damage) {
        this.healthPoint -= damage;

        if (this.healthPoint <= 0) {
            onDeath(); // Appeler une méthode pour gérer la mort
        }
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVue(EntitieVue vue) {
        this.vue = vue;
    }

    public EntitieVue getVue() {
        return vue;
    }
}
