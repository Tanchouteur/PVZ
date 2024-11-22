package fr.tanchou.pvz.entities.zombie;

import fr.tanchou.pvz.entities.Effect;
import fr.tanchou.pvz.entities.Entitie;

public abstract class Zombie extends Entitie {
    private int speed;
    private Effect effect;
    private boolean isDead = false;

    protected Zombie(int speed, int healthPoint, Double x, int y) {
        super(healthPoint, x, y);
        this.speed = speed;
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

    public void move() {
        this.setX(this.getX() - speed*0.01);
        //System.out.println("Zombie avance à la position " + this.getX());
    }

    @Override
    public void onDeath() {
        this.getVue().getImageView().setVisible(false);
        isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }
}