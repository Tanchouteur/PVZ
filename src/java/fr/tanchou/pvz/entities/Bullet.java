package fr.tanchou.pvz.entities;

import fr.tanchou.pvz.entities.zombie.Zombie;

public abstract class Bullet extends Entitie {
    private int damage;
    private int speed;
    private Effect effect;

    public Bullet(int damage, int speed, Double x, int y) {
        super(1000, x, y);

        this.damage = damage;
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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
        this.setX(this.getX() + speed*0.5);
        System.out.println("bullet avance à la position " + this.getX() + " test " + (this.getX() + (speed/100)));
    }

    public boolean collidesWith(Zombie zombie) {
        // Différence entre la position du projectile et celle du zombie
        double distance = Math.abs(this.getX() - zombie.getX());

        // Taille ou rayon défini pour détecter la collision
        double collisionThreshold = 0.4; // Par exemple, 0.5 unité de distance

        return distance <= collisionThreshold;
    }

    @Override
    public void onDeath() {
        System.out.println("Bullet est mort");
    }

}
