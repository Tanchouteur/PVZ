package fr.tanchou.pvz.entities.plants.shooter.pea;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.plants.Plant;
import fr.tanchou.pvz.entities.plants.shooter.ShooterPlant;
import javafx.scene.layout.Pane;

public class PeaShooter extends ShooterPlant {
    public PeaShooter() {
        super(120, "PeaShooter", 100, 5, 100);
    }

    @Override
    protected Bullet createBullet() {
        return new PeaBullet(this.getX(), this.getY(), 20, 10);
    }

    @Override
    public PeashooterVue createVue(Pane parent) {
        return new PeashooterVue(this, parent);
    }
}

