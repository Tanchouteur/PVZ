package fr.tanchou.pvz.entities.zombie;

import fr.tanchou.pvz.entities.Effect;
import fr.tanchou.pvz.entities.Entitie;
import fr.tanchou.pvz.entities.plants.Plant;

public abstract class Zombie extends Entitie {
    private int speed;
    private Effect effect;
    private boolean isDead = false;
    private final int damage;

    protected Zombie(int speed, int healthPoint, int damage, Double x, int y) {
        super(healthPoint, x, y);
        this.speed = speed;
        this.damage = damage;
    }

    public boolean collidesWith(Plant plant) {

        double distance = Math.abs(this.getX() - plant.getX());

        double collisionThreshold = 0.4; // Par exemple, 0.5 unité de distance

        return distance <= collisionThreshold;
    }

    @Override
    public void onDeath() {
        this.getVue().getImageView().setVisible(false);
        isDead = true;
    }

    public void move() {
        this.setX(this.getX() - speed*0.01);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public int getDamage() {
        return damage;
    }
}