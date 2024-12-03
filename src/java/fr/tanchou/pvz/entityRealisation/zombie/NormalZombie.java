package fr.tanchou.pvz.entityRealisation.zombie;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public class NormalZombie extends Zombie {

    public NormalZombie(Double x, int y) {
        super(100, .3,x, y, 1, 20, 24);
    }

    @Override
    public Zombie clone(double x, int y) {
        return new NormalZombie(x, y);
    }
}
