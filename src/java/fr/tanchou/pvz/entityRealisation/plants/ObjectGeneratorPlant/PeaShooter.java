package fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant;

import fr.tanchou.pvz.abstractEnity.abstractPlant.ObjectGeneratorsPlant;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.PeaBullet;

public class PeaShooter extends ObjectGeneratorsPlant {

    public PeaShooter(double x, int y) {
        super(100, .3 , x, y, "PeaShooter", 50, 15, new PeaBullet(x+0.1 , y));
    }

    @Override
    protected boolean canCreate() {
        if (this.getTimeSinceLastFire() >= this.getFireRate() && this.isZombieInFront()) {
            this.setTimeSinceLastFire(); // Réinitialise le compteur après un tir

            return true;
        }
        return false;
    }

    @Override
    public ObjectGeneratorsPlant clone(double x, int y) {
        return new PeaShooter(x, y);
    }
}

