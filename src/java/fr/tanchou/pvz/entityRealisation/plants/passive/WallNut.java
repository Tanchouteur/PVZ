package fr.tanchou.pvz.entityRealisation.plants.passive;

import fr.tanchou.pvz.abstractEnity.abstractPlant.PassivePlant;
import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;

public class WallNut extends PassivePlant {


    public WallNut(double x, int y) {
        super(500, .2, x, y, "WallNut", 50);
    }

    @Override
    public Plant clone(double x, int y) {
        return new WallNut(x, y);
    }
}
