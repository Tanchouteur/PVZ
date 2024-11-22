package fr.tanchou.pvz.entities.plants.pea;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.EntitieVue;
import javafx.scene.layout.Pane;

public class PeaBullet extends Bullet {

    public PeaBullet(Double x, int y, int damage, int speed) {
        super(damage, speed, x, y);
    }

    @Override
    public EntitieVue createVue(Pane parent) {
        return new PeaBulletVue(this);
    }
}
