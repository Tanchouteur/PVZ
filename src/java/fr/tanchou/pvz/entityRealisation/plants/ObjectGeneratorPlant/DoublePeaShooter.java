package fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant;

import fr.tanchou.pvz.abstractEnity.abstractPlant.ObjectGeneratorsPlant;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.PeaBullet;

public class DoublePeaShooter extends ObjectGeneratorsPlant {

    public DoublePeaShooter(double x, int y) {
        super(100, .3 , (int) x, y, "DoublePeaShooter", 200, 7, new PeaBullet(x+0.1 , y));
    }

    @Override
    public ObjectGeneratorsPlant clone(double x, int y) {
        return new DoublePeaShooter(x, y);
    }
}

