package fr.tanchou.pvz.entities.zombie.conheadZombie;

import fr.tanchou.pvz.entities.EntitieVue;
import fr.tanchou.pvz.entities.zombie.Zombie;
import javafx.scene.layout.Pane;

public class ConeheadZombie extends Zombie {

    public ConeheadZombie(Double x, int y) {
        super(1, 200,25, x, y);
    }

    @Override
    public EntitieVue createVue(Pane parent) {
        return new ConeheadZombieVue(this, parent);
    }
}
