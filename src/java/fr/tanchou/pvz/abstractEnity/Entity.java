package fr.tanchou.pvz.abstractEnity;

public abstract class Entity {
    private int healthPoint;
    private final Collider collider;
    private double x;
    private final int y;

    public Entity(int healthPoint, int colliderRadius, double x, int y) {
        this.healthPoint = healthPoint;
        this.collider = new Collider(colliderRadius);
        this.x = x;
        this.y = y;
    }

    public abstract Entity clone(double x, int y);

    boolean collidesWith(Entity entity) {
        return Math.sqrt(Math.pow(this.getCollider().getRadius() + entity.getCollider().getRadius(), 2) - Math.pow(this.getX() - entity.getX(), 2) - Math.pow(this.getY() - entity.getY(), 2)) >= 0;
    }

    public int takeDamage(int damage) {
        this.healthPoint -= damage;
        return this.healthPoint;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public Collider getCollider() {
        return collider;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
}
