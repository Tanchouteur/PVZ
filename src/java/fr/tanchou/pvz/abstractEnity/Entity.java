package fr.tanchou.pvz.abstractEnity;

public abstract class Entity {
    private int originalHealth;
    private int healthPoint;
    private final Collider collider;
    private double x;
    private final int y;
    private final String name;
    private boolean isDead = false;

    public Entity(int healthPoint, double colliderRadius, double x, int y, String name) {
        this.healthPoint = healthPoint;
        this.originalHealth = healthPoint;
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

    public void takeDamage(int damage) {
        this.healthPoint -= damage;
        if (this.healthPoint <= 0) {
            this.isDead = true;
        }
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
        if (this.healthPoint <= 0) {
            this.isDead = true;
        }
        return isDead;
    }

    public int getOriginalHealth() {
        return originalHealth;
    }

    public void setOriginalHealth(int originalHealth) {
        this.originalHealth = originalHealth;
    }
}
