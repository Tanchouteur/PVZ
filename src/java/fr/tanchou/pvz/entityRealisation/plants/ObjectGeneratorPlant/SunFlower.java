package fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant;

import fr.tanchou.pvz.abstractEnity.abstractPlant.ObjectGeneratorsPlant;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;

public class SunFlower extends ObjectGeneratorsPlant {

    public SunFlower(double x, int y) {
        super(100, .3 , (int) x, y, "SunFlower", 50, 100, new Sun(x + 0.5, y, 25));
    }

    @Override
    protected boolean canCreate() {
        if (getTimeSinceLastFire() >= getFireRate()) {
            setTimeSinceLastFire(); // Réinitialise le compteur après un tir
            return true;
        }
        return false;
    }

    @Override
    public ObjectGeneratorsPlant clone(double x, int y) {
        return new SunFlower(x, y);
    }
}