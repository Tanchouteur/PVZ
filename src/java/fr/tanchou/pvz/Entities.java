package fr.tanchou.pvz;

public abstract class Entities {
    private int health = 0;
    Double positionX = 0.0;

    public void removeHealth(int damage) {
        this.health -= damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
