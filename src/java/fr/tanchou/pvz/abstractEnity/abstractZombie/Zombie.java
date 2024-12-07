package fr.tanchou.pvz.abstractEnity.abstractZombie;

import fr.tanchou.pvz.abstractEnity.Effect;
import fr.tanchou.pvz.abstractEnity.Entity;

public abstract class Zombie extends Entity {
    private final int speed;
    private Effect effect;
    private final int damage;
    protected int timeSinceLastAttack = 0;
    private final int attackRate;
    private boolean heating = false;

    protected Zombie(int healthPoint, double collideRadius, double x, int y, int speed, int damage, int attackRate, String name) {
        super(healthPoint, collideRadius,x, y, name);
        this.speed = speed;
        this.damage = damage;
        this.attackRate = attackRate;

    }

    public abstract Zombie clone(double x, int y);

    private boolean canAttack() {
        int calculedAttackRate = attackRate;
        if (effect != null){
            calculedAttackRate = (int) (calculedAttackRate * effect.getSpeedToApply());
        }
        if (timeSinceLastAttack >= calculedAttackRate) {
            timeSinceLastAttack = 0; // Réinitialise le compteur après un tir
            return true;
        }
        return false;
    }

    public void attack(Entity entity) {
        if (canAttack()){
            entity.takeDamage(damage);
        }
    }

    public void move() {
        if (!heating) {
            double calculatedSpeed = this.speed;
            if (effect != null) {
                effect.tick();
                calculatedSpeed = calculatedSpeed * effect.getSpeedToApply();
            }
            this.setX(this.getX() - (calculatedSpeed * 0.020));
        }
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public void tick() {
        if (effect != null) {
            effect.tick();

            if (effect.getEffectDuration() <= 0){
                this.effect = null;
            }else {
                if (effect.getEffectDuration()%10 == 0){
                    this.takeDamage(effect.getDammagePer10Tick());
                }
            }
        }
        timeSinceLastAttack++;
    }

    public void setHeating(boolean heating) {
        this.heating = heating;
    }

    @Override
    public String toString() {
        return this.getName() + "{ hp=" + this.getHealthPoint() + ", position="+this.getX()+" effect = "+ this.effect +" heating ="+heating+" }";
    }

    public boolean heating() {
        return heating;
    }

    public Object getEffect() {
        return effect;
    }
}