package fr.tanchou.pvz.abstractEnity.abstractPlant;


import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.ObjectOfPlant;

public abstract class ObjectGeneratorsPlant extends Plant {
    private final int fireRate;
    private int timeSinceLastFire = 0;
    private final ObjectOfPlant objectOfPlant;
    private boolean zombieInFront = false;

    protected ObjectGeneratorsPlant(int healthPoint, int colliderRadius, double x, int y,String name, int cost, int fireRate, ObjectOfPlant objectOfPlant) {
        super(healthPoint,colliderRadius,x,y, name, cost);
        this.fireRate = fireRate;
        this.objectOfPlant = objectOfPlant;
    }

    public ObjectOfPlant fire() {
        if (canCreate()) {
            return objectOfPlant.clone(); // Délègue à une méthode qui retourne un projectile concret
        }
        return null;
    }

    protected abstract boolean canCreate();

    public ObjectOfPlant tick() {
        if (timeSinceLastFire < fireRate) {
            timeSinceLastFire++;
            return null;
        }else {
            timeSinceLastFire = 0;
            fire();
        }
        return null;
    }

    public abstract ObjectGeneratorsPlant clone(double x, int y);

    public ObjectOfPlant getObjectOfPlant() {
        return objectOfPlant;
    }

    public int getFireRate() {
        return fireRate;
    }

    public int getTimeSinceLastFire() {
        return timeSinceLastFire;
    }

    public void setTimeSinceLastFire() {
        this.timeSinceLastFire = 0;
    }

    public boolean isZombieInFront() {
        return zombieInFront;
    }

    public void setZombieInFront(boolean zombieInFront) {
        this.zombieInFront = zombieInFront;
    }
}
