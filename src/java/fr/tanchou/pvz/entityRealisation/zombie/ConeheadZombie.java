package fr.tanchou.pvz.entityRealisation.zombie;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public class ConeheadZombie extends Zombie {

    public ConeheadZombie(Double x, int y) {
        super(200, 1,x, y, 1, 10, 24, "ConeheadZombie");
    }

    @Override
    public Zombie clone(double x, int y) {
        return new ConeheadZombie(x, y);
    }
}

