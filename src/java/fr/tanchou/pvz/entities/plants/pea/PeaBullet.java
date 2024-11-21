package fr.tanchou.pvz.entities.plants.pea;

import fr.tanchou.pvz.entities.Bullet;

public class PeaBullet extends Bullet {

    public PeaBullet(Double position, int damage, int speed) {
        super(damage, speed);
        this.setPosition(position);
    }
}
