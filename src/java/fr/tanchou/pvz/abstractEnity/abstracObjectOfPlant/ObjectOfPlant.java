package fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant;

import fr.tanchou.pvz.abstractEnity.Collider;

public abstract class ObjectOfPlant {
    private double x;
    private int y;
    private final Collider collider;

    public ObjectOfPlant(double x, int y, int colliderRadius) {
        this.x = x;
        this.y = y;
        this.collider = new Collider(colliderRadius);
    }

    public abstract ObjectOfPlant clone();

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Collider getCollider() {
        return collider;
    }
}
