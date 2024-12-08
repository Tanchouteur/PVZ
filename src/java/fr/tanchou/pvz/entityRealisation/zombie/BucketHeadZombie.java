package fr.tanchou.pvz.entityRealisation.zombie;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public class BucketHeadZombie extends Zombie {

    public BucketHeadZombie(Double x, int y) {
        super(320, .3, x, y, 1, 15, 6, "BucketHead");
    }

    @Override
    public Zombie clone(double x, int y) {
        return new BucketHeadZombie(x, y);
    }
}

