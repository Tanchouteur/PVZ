package fr.tanchou.pvz.entities;

import fr.tanchou.pvz.entities.plants.PlantVue;

public abstract class Entitie {
    private Double x;
    private int y;
    private int healthPoint;


    public Entitie(int healthPoint) {
        this.healthPoint = healthPoint;
        createVue();
    }

    public abstract EntitieVue createVue();

    public abstract void onDeath();

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public void takeDamage(int damage) {
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
}
