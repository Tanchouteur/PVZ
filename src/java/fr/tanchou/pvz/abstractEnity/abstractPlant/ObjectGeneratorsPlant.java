package fr.tanchou.pvz.abstractEnity.abstractPlant;


import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.ObjectOfPlant;

public abstract class ObjectGeneratorsPlant extends Plant {
    private final int fireRate;
    private int timeSinceLastFire = 0;
    private final ObjectOfPlant objectOfPlant;
    private boolean needToCreate;

    protected ObjectGeneratorsPlant(int healthPoint, double colliderRadius, int x, int y,String name, int cost, int fireRate, ObjectOfPlant objectOfPlant) {
        super(healthPoint,colliderRadius,x,y, name, cost);
        this.fireRate = fireRate;
        this.timeSinceLastFire = getTimeSinceLastFireAtSpawn();
        this.objectOfPlant = objectOfPlant;
    }

    public ObjectOfPlant fire() {
        if (canCreate()) {
            return objectOfPlant.clone();
        }
        return null;
    }

    protected boolean canCreate(){
        if (this.getTimeSinceLastFire() >= this.getFireRate() && this.isNeedToCreate()) {
            this.setTimeSinceLastFire(); // Réinitialise le compteur après un tir

            return true;
        }
        return false;
    }

    public ObjectOfPlant tick() {
        timeSinceLastFire++;
        return fire();

    }

    public abstract ObjectGeneratorsPlant clone(double x, int y);

    public int getFireRate() {
        return fireRate;
    }

    public int getTimeSinceLastFire() {
        return timeSinceLastFire;
    }

    public void setTimeSinceLastFire() {
        this.timeSinceLastFire = 0;
    }

    public abstract int getTimeSinceLastFireAtSpawn();

    public boolean isNeedToCreate() {
        return needToCreate;
    }

    public void setNeedToCreate(boolean needToCreate) {
        this.needToCreate = needToCreate;
    }

    public ObjectOfPlant getObjectOfPlant() {
        return objectOfPlant;
    }
}
