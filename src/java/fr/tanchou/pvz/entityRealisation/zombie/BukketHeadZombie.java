package fr.tanchou.pvz.entityRealisation.zombie;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public class BukketHeadZombie extends Zombie {

    public BukketHeadZombie(Double x, int y) {
        super(320, .1,x, y, 1, 10, 24, "BucketHead");
    }

    @Override
    public Zombie clone(double x, int y) {
        return new BukketHeadZombie(x, y);
    }
}

