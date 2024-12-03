package fr.tanchou.pvz.entityRealisation.ObjectOfPlant;

import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.Bullet;
import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.ObjectOfPlant;

public class PeaBullet extends Bullet {

    public PeaBullet(Double x, int y) {
        super(25, 1, x, y, .2, null);
    }

    @Override
    public ObjectOfPlant clone() {
        return new PeaBullet(this.getX(), this.getY());
    }
}
