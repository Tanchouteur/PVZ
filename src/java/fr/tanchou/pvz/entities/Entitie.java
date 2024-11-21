package fr.tanchou.pvz.entities;

public abstract class Entitie {
    private Double position;
    private int healthPoint;

    public Entitie(int healthPoint) {
        this.healthPoint = healthPoint;
    }

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

    public abstract void onDeath();

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }
}
