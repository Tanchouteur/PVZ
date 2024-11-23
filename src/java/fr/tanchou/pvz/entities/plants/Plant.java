package fr.tanchou.pvz.entities.plants;

import fr.tanchou.pvz.entities.Entitie;

public abstract class Plant extends Entitie {

    private PlantCards card;
    protected int timeSinceLastShot = 0;
    private boolean isDead = false;

    protected Plant(String name, int cost, int cooldownToBuy, int healthPoint) {
        super(healthPoint,-1.0,-1);
        this.card = new PlantCards(name, cost, cooldownToBuy);
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

    public int getTimeSinceLastShot() {
        return timeSinceLastShot;
    }
}