package fr.tanchou.pvz.entityRealisation.ObjectOfPlant;

import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.Bullet;
import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.ObjectOfPlant;

public class PeaBullet extends Bullet {

    public PeaBullet(Double x, int y) {
        super(12, 1, x, y, .1, null, "PeaBullet");
    }

    @Override
    public ObjectOfPlant clone() {
        return new PeaBullet(this.getX(), this.getY());
    }
}
