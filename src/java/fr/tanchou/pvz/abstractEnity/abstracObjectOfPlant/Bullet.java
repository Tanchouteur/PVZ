package fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant;

import fr.tanchou.pvz.abstractEnity.Effect;
import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public abstract class Bullet extends ObjectOfPlant {
    private final int damage;
    private final int speed;
    private final Effect effect;

    public Bullet(int damage, int speed, Double x, int y, int colliderRadius,Effect effect) {
        super(x, y, colliderRadius);
        this.damage = damage;
        this.speed = speed;
        this.effect = effect;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public Effect getEffect() {
        return effect;
    }

    public void move() {
        this.setX(this.getX() + speed*0.01);
        //System.out.println("bullet avance à la position " + this.getX());
    }

    public boolean collidesWith(Zombie zombie) {
        // Différence entre la position du projectile et celle du zombie
        double distance = Math.abs(this.getX() - zombie.getX());

        return distance <= this.getCollider().getRadius()+zombie.getCollider().getRadius();
    }
}
