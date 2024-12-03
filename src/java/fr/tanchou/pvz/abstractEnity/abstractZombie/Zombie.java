package fr.tanchou.pvz.abstractEnity.abstractZombie;

import fr.tanchou.pvz.abstractEnity.Effect;
import fr.tanchou.pvz.abstractEnity.Entity;
import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;

public abstract class Zombie extends Entity {
    private int speed;
    private Effect effect;
    private final int damage;
    protected int timeSinceLastAttack = 0;
    private final int attackRate;
    private boolean heating = false;

    protected Zombie(int healthPoint, double collideRadius, double x, int y, int speed, int damage, int attackRate) {
        super(healthPoint, collideRadius,x, y);
        this.speed = speed;
        this.damage = damage;
        this.attackRate = attackRate;
    }

    public abstract Zombie clone(double x, int y);

    private boolean canAttack() {
        if (timeSinceLastAttack >= 24) {
            timeSinceLastAttack = 0; // Réinitialise le compteur après un tir
            return true;
        }
        return false;
    }

    public int attack(Entity entity) {
        if (canAttack()){
            System.err.println("Zombie attack : " + entity.getX());
            return entity.takeDamage(damage);
        }
        return 1001;
    }

    public void move() {
        if (!heating) {
            this.setX(this.getX() - speed * 0.05);
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

    @Override
    public String toString() {
        return "Zombie{ hp=" + this.getHealthPoint() + ", position="+this.getX()+","+this.getY()+" heating ="+heating+" }";
    }
}