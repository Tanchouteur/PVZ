package fr.tanchou.pvz.abstractEnity.abstractZombie;

import fr.tanchou.pvz.abstractEnity.Effect;
import fr.tanchou.pvz.abstractEnity.Entity;

public abstract class Zombie extends Entity {
    private int speed;
    private Effect effect;
    private final int damage;
    protected int timeSinceLastAttack = 0;
    private final int attackRate;
    private boolean heating = false;

    protected Zombie(int healthPoint, int collideRadius, double x, int y, int speed, int damage, int attackRate) {
        super(healthPoint, collideRadius,x, y);
        this.speed = speed;
        this.damage = damage;
        this.attackRate = attackRate;
    }

    public abstract Zombie clone(double x, int y);

    private boolean canAttack() {
        if (timeSinceLastAttack >= 100) {
            timeSinceLastAttack = 0; // Réinitialise le compteur après un tir
            return true;
        }
        return false;
    }

    public void attack(Entity entity) {
        if (canAttack()){
            entity.takeDamage(damage);
            //System.out.println("Zombie attack : " + enitiy);
        }
    }

    public void move() {
        if (!heating) {
            this.setX(this.getX() - speed * 0.01);
        }
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

    public int getDamage() {
        return damage;
    }

    public void tick() {
        timeSinceLastAttack++;
    }

    public int getAttackRate() {
        return attackRate;
    }

    public boolean heating() {
        return heating;
    }

    public void setHeating(boolean heating) {
        this.heating = heating;
    }
}