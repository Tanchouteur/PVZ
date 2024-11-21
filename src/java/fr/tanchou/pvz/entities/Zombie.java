package fr.tanchou.pvz.entities;

public abstract class Zombie extends Entitie {
    private int speed;
    private Effect effect;

    protected Zombie(int speed, int healthPoint) {
        super(healthPoint);
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
        this.setPosition(this.getPosition() - speed/100);
        System.out.println("Zombie avance à la position " + this.getPosition());
    }

    @Override
    public void onDeath() {
        System.out.println("Zombie est mort");
    }
}