package fr.tanchou.pvz.abstractEnity.abstractPlant;


import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.ObjectOfPlant;

public abstract class ObjectGeneratorsPlant extends Plant {
    private final int fireRate;
    private int timeSinceLastFire = 0;
    private final ObjectOfPlant objectOfPlant;

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

    protected boolean canCreate() {
        if (timeSinceLastFire >= fireRate) {
            timeSinceLastFire = 0; // Réinitialise le compteur après un tir
            return true;
        }
        return false;
    }

    public void tick() {
        if (timeSinceLastFire < fireRate) {
            timeSinceLastFire++;
        }
    }

    public abstract ObjectGeneratorsPlant clone(double x, int y);
}
