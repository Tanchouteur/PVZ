package fr.tanchou.pvz.entities.zombie.normalZombie;

import fr.tanchou.pvz.entities.EntitieVue;
import fr.tanchou.pvz.entities.zombie.Zombie;
import javafx.scene.layout.Pane;

public class NormalZombie extends Zombie {

    public NormalZombie(Double x, int y) {
        super(1, 200,25, x, y);
    }

    @Override
    public EntitieVue createVue(Pane parent) {
        return new NormalZombieVue(this, parent);
    }
}
