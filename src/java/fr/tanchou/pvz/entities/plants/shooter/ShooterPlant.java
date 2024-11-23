package fr.tanchou.pvz.entities.plants.shooter;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.plants.Plant;

public abstract class ShooterPlant extends Plant {

    private int shootSpeed;

    protected ShooterPlant(int shootSpeed, String name, int cost, int cooldownToBuy, int healthPoint) {
        super(name, cost, cooldownToBuy, healthPoint);
        this.shootSpeed = shootSpeed;
    }

    protected abstract Bullet createBullet();

    public Bullet shoot() {
        if (canShoot()) {
            return createBullet(); // Délègue à une méthode qui retourne un projectile concret
        }
        return null;
    }

    protected boolean canShoot() {
        if (getTimeSinceLastShot() >= shootSpeed) {
            timeSinceLastShot = 0; // Réinitialise le compteur après un tir
            return true;
        }
        return false;
    }

    public void setShootSpeed(int shootSpeed) {
        this.shootSpeed = shootSpeed;
    }

    public int getShootSpeed() {
        return shootSpeed;
    }
}
