package fr.tanchou.pvz.entityRealisation.ObjectOfPlant;

import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.Bullet;
import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.ObjectOfPlant;
import fr.tanchou.pvz.entityRealisation.effect.FreezeEffect;

public class FreezePeaBullet extends Bullet {

    public FreezePeaBullet(Double x, int y) {
        super(15, 1, x, y, .1, new FreezeEffect(), "FreezePeaBullet");
    }

    @Override
    public ObjectOfPlant clone() {
        return new FreezePeaBullet(this.getX(), this.getY());
    }
}
