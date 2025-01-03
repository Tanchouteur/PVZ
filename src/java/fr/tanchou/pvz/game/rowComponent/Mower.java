package fr.tanchou.pvz.game.rowComponent;

import fr.tanchou.pvz.abstractEnity.Collider;
import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public class Mower {
    private double x;
    private final int y;
    private final Collider collider;
    private boolean active = false;

    public Mower(int y) {
        this.x = -0.5;
        this.collider = new Collider(.2);
        this.y = y;
    }

    public void move() {

        this.x += 0.3;

    }

    public boolean collideWith(Zombie zombie) {
        double distance = Math.abs(this.getX() - zombie.getX());

        return distance <= this.collider.getRadius()+zombie.getCollider().getRadius();
    }

    public double getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString(){
        return ""+this.getX();
    }
}
