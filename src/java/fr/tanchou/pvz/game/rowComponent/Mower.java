package fr.tanchou.pvz.game.rowComponent;

import fr.tanchou.pvz.abstractEnity.Collider;
import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public class Mower {
    private final double x;
    private final Collider collider;

    public Mower() {
        this.x = -0.5;
        this.collider = new Collider(.2);
    }

    public boolean collideWith(Zombie zombie) {
        double distance = Math.abs(this.getX() - zombie.getX());

        return distance <= this.collider.getRadius()+zombie.getCollider().getRadius();
    }

    public double getX() {
        return x;
    }

    @Override
    public String toString(){
        return ""+this.getX();
    }
}
