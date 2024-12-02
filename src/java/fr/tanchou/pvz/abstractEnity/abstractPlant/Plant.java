package fr.tanchou.pvz.abstractEnity.abstractPlant;

import fr.tanchou.pvz.abstractEnity.Entity;

public abstract class Plant extends Entity {
    private final String name;
    private final int cost;

    protected Plant(int healthPoint, int colliderRadius, double x, int y,String name, int cost) {
        super(healthPoint,colliderRadius,x,y);
        this.name = name;
        this.cost = cost;
    }

    public abstract Plant clone(double x, int y);

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "name=" + this.getName() +
                '}';
    }
}