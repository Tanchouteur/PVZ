package fr.tanchou.pvz.abstractEnity.abstractPlant;

public abstract class PassivePlant extends Plant {
    public PassivePlant(int healthPoint, double colliderRadius, double x, int y,String name, int cost) {
        super(healthPoint,colliderRadius, (int) x,y, name, cost);
    }

}
