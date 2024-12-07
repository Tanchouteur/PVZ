package fr.tanchou.pvz.entityRealisation.zombie;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public class ConeHeadZombie extends Zombie {

    public ConeHeadZombie(Double x, int y) {
        super(200, .2,x, y, 1, 15, 6, "ConeHead");
    }

    @Override
    public Zombie clone(double x, int y) {
        return new ConeHeadZombie(x, y);
    }
}

