package fr.tanchou.pvz.entities;

import fr.tanchou.pvz.entities.zombie.Zombie;

public abstract class Bullet {
    private int damage;
    private int speed;
    private Double position;
    private Effect effect;

    public Bullet(int damage, int speed) {

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

    public Double getPosition() {
        return position;
    }

    public void setPosition(Double position) {
        this.position = position;
    }

    public void move() {
        this.position -= (double) speed /100;
    }

    public boolean collidesWith(Zombie zombie) {
        // Différence entre la position du projectile et celle du zombie
        double distance = Math.abs(this.position - zombie.getX());

        // Taille ou rayon défini pour détecter la collision
        double collisionThreshold = 0.4; // Par exemple, 0.5 unité de distance

        return distance <= collisionThreshold;
    }

}
