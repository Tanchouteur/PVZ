package fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant;

import fr.tanchou.pvz.abstractEnity.abstractPlant.ObjectGeneratorsPlant;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;

public class SunFlower extends ObjectGeneratorsPlant {

    public SunFlower(double x, int y) {
        super(100, 1 , x, y, "SunFlower", 50, 120, new Sun(x + 0.5, y, 1, 25));
    }

    @Override
    public ObjectGeneratorsPlant clone(double x, int y) {
        return new SunFlower(x, y);
    }
}