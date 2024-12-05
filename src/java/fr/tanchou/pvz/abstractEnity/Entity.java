package fr.tanchou.pvz.abstractEnity;

public abstract class Entity {
    private int healthPoint;
    private final Collider collider;
    private double x;
    private final int y;
    private final String name;
    private boolean isDead = false;

    public Entity(int healthPoint, double colliderRadius, double x, int y, String name) {
        this.healthPoint = healthPoint;
        this.collider = new Collider(colliderRadius);
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public abstract Entity clone(double x, int y);

    public boolean collideWith(Entity entity) {
        double distance = Math.abs(entity.getX() - this.getX());

        return distance <= this.getCollider().getRadius()+entity.getCollider().getRadius();
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

    public String getName() {
        return name;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
