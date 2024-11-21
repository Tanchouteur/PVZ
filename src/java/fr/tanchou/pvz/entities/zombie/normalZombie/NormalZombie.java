package fr.tanchou.pvz.entities.zombie.normalZombie;

import fr.tanchou.pvz.entities.EntitieVue;
import fr.tanchou.pvz.entities.zombie.Zombie;

public class NormalZombie extends Zombie {

    public NormalZombie(Double x, int y) {
        super(100, 100, x, y);
    }

    @Override
    public EntitieVue createVue() {
        return new NormalZombieVue(this);
    }
}
