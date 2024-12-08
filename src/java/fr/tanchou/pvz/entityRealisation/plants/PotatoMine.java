package fr.tanchou.pvz.entityRealisation.plants;

import fr.tanchou.pvz.abstractEnity.abstractPlant.WaitingPlant;

public class PotatoMine extends WaitingPlant {
    public PotatoMine(int x, int y) {
        super(100, 0.4, x, y, "PotatoMine", 25, 200, 400);
    }

    @Override
    public WaitingPlant clone(double x, int y) {
        return new PotatoMine((int) x, y);
    }
}
