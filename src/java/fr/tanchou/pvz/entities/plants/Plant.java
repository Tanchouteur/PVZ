package fr.tanchou.pvz.entities.plants;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.Entitie;

public abstract class Plant extends Entitie {
    private int shootSpeed;
    private PlantCards card;
    protected int timeSinceLastShot = 0;

    protected Plant(int shootSpeed, String name, int cost, int cooldown, int healthPoint) {
        super(healthPoint);
        this.shootSpeed = shootSpeed;
        this.card = new PlantCards(name, cost, cooldown);
    }

    public Bullet shoot() {
        if (canShoot()) {
            return createBullet(); // Délègue à une méthode qui retourne un projectile concret
        }
        return null;
    }

    protected abstract Bullet createBullet();

    protected boolean canShoot() {
        if (timeSinceLastShot >= shootSpeed) {
            timeSinceLastShot = 0; // Réinitialise le compteur après un tir
            return true;
        }
        return false;
    }

    public void tick() {
        timeSinceLastShot++;
    }

    @Override
    public void onDeath() {
        System.out.println("Plant est mort");
    }

    public void setShootSpeed(int shootSpeed) {
        this.shootSpeed = shootSpeed;
    }

    public int getShootSpeed() {
        return shootSpeed;
    }

    public PlantCards getCard() {
        return card;
    }

    public void setCard(PlantCards card) {
        this.card = card;
    }
}