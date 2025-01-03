package fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant;

import fr.tanchou.pvz.abstractEnity.Effect;
import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public abstract class Bullet extends ObjectOfPlant {
    private final int damage;
    private final int speed;
    private final Effect effect;
    private final String name;
    private boolean dead = false;

    public Bullet(int damage, int speed, Double x, int y, double colliderRadius, Effect effect, String name) {
        super(x, y, colliderRadius);
        this.damage = damage;
        this.speed = speed;
        this.effect = effect;
        this.name = name;
    }

    public void move() {
        this.setX(this.getX() + speed*0.3);
        //System.out.println("bullet avance à la position " + this.getX());
    }

    public boolean collidesWith(Zombie zombie) {
        // Différence entre la position du projectile et celle du zombie
        double distance = Math.abs(this.getX() - zombie.getX());

        return distance <= this.getCollider().getRadius()+zombie.getCollider().getRadius();
    }

    public int getDamage() {
        return damage;
    }

    public Effect getEffect() {
        return effect;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getName() + "{" +
                "x=" + this.getX() +
                ", y=" + this.getY() +
                '}';
    }

    public boolean haveEffect(){
        return this.effect != null;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }
}
