package fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant;

import fr.tanchou.pvz.abstractEnity.abstractPlant.ObjectGeneratorsPlant;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.PeaBullet;

public class PeaShooter extends ObjectGeneratorsPlant {

    public PeaShooter(double x, int y) {
        super(100, 1 , x, y, "PeaShooter", 50, 48, new PeaBullet(x+0.1 , y));
    }

    @Override
    public ObjectGeneratorsPlant clone(double x, int y) {
        return new PeaShooter(x, y);
    }
}

