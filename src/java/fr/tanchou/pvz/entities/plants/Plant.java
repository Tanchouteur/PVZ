package fr.tanchou.pvz.entities.plants;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.Entitie;

public abstract class Plant extends Entitie {
    private int shootSpeed;
    private PlantCards card;
    protected int timeSinceLastShot = 0;
    private boolean isDead = false;

    protected Plant(int shootSpeed, String name, int cost, int cooldown, int healthPoint, Double x, int y) {
        super(healthPoint,x,y);
        this.shootSpeed = shootSpeed;
        this.card = new PlantCards(name, cost, cooldown);
    }

    protected abstract Bullet createBullet(int y);

    public Bullet shoot() {
        if (canShoot()) {
            return createBullet(this.getY()); // Délègue à une méthode qui retourne un projectile concret
        }
        return null;
    }

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
        if (this.getVue() != null)
            this.getVue().getImageView().setVisible(false);
        isDead = true;
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

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}