package fr.tanchou.pvz.entities.plants.pea;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.BulletVue;
import fr.tanchou.pvz.entities.EntitieVue;
import javafx.scene.image.ImageView;

import java.net.URL;

import static java.lang.System.exit;

public class PeaBullet extends Bullet {

    public PeaBullet(Double position, int damage, int speed) {
        super(damage, speed);
        this.setPosition(position);
    }

    @Override
    public EntitieVue createVue() {

        return new PeaBulletVue(this);
    }
}
