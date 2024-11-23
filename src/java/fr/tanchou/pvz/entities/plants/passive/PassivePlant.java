package fr.tanchou.pvz.entities.plants.passive;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.plants.Plant;

public abstract class PassivePlant extends Plant {
    private final int actionRate;
    public PassivePlant(String name, int cost, int cooldownToBuy, int healthPoint, int actionRate) {
        super(name, cost, cooldownToBuy, healthPoint);
        this.actionRate = actionRate;
    }

    public abstract Bullet action();


    protected boolean canDo() {
        if (getTimeSinceLastShot() >= actionRate) {
            timeSinceLastShot = 0; // Réinitialise le compteur après un tir
            return true;
        }
        return false;
    }

    public int getActionRate() {
        return actionRate;
    }
}
