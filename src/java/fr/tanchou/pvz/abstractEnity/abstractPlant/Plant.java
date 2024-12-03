package fr.tanchou.pvz.abstractEnity.abstractPlant;

import fr.tanchou.pvz.abstractEnity.Entity;

public abstract class Plant extends Entity {
    private final int cost;

    protected Plant(int healthPoint, double colliderRadius, double x, int y,String name, int cost) {
        super(healthPoint,colliderRadius,x,y, name);
        this.cost = cost;
    }

    public abstract Plant clone(double x, int y);

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getHealthPoint();
    }
}