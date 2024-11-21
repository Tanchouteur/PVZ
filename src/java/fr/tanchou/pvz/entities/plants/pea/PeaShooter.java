package fr.tanchou.pvz.entities.plants.pea;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.plants.Plant;

public class PeaShooter extends Plant {
    public PeaShooter() {
        super(100, "PeaShooter", 100, 5, 100);
    }

    @Override
    protected Bullet createBullet() {
        return new PeaBullet(this.getX(), 20, 1);
    }

    @Override
    public PeashooterVue createVue() {
        return new PeashooterVue(this);
    }
}

