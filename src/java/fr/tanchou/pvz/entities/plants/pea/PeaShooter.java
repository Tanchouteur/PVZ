package fr.tanchou.pvz.entities.plants.pea;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.plants.Plant;
import javafx.scene.layout.Pane;

public class PeaShooter extends Plant {
    public PeaShooter(Double x, int y) {
        super(100, "PeaShooter", 100, 5, 100, x, y);
    }

    @Override
    protected Bullet createBullet(int y) {
        return new PeaBullet(this.getX(), y, 20, 10);
    }

    @Override
    public PeashooterVue createVue(Pane parent) {
        return new PeashooterVue(this, parent);
    }
}

