package fr.tanchou.pvz.entities;

public abstract class Plant extends Entitie {
    private int shootSpeed;
    private PlantCards card;

    protected Plant(int shootSpeed, String name, int cost, int cooldown) {
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

        return true;
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