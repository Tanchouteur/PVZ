package fr.tanchou.pvz.abstractEnity;

public class Collider {
    private final double radius;

    public Collider(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Collider{" +
                "radius=" + radius +
                '}';
    }
}
