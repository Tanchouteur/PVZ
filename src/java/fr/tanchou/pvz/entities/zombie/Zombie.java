package fr.tanchou.pvz.entities.zombie;

import fr.tanchou.pvz.entities.Effect;
import fr.tanchou.pvz.entities.Entitie;

public abstract class Zombie extends Entitie {
    private int speed;
    private Effect effect;

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
        System.out.println("Zombie avance à la position " + this.getX());
    }

    @Override
    public void onDeath() {
        System.out.println("Zombie est mort");
    }
}