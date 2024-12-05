package fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant;

import fr.tanchou.pvz.abstractEnity.abstractPlant.ObjectGeneratorsPlant;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.FreezePeaBullet;

public class FreezePeaShooter extends ObjectGeneratorsPlant {

    public FreezePeaShooter(double x, int y) {
        super(100, .3 , x, y, "FreezePeaShooter", 175, 15, new FreezePeaBullet(x+0.1 , y));
    }

    @Override
    public ObjectGeneratorsPlant clone(double x, int y) {
        return new FreezePeaShooter(x, y);
    }
}

