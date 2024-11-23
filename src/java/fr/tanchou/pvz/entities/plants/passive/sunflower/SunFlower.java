package fr.tanchou.pvz.entities.plants.passive.sunflower;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.plants.passive.PassivePlant;
import javafx.scene.layout.Pane;

public class SunFlower extends PassivePlant {
    public SunFlower() {
        super( "SunFlower", 50, 5, 100, 400);
    }

    @Override
    public SunFlowerVue createVue(Pane parent) {
        return new SunFlowerVue(this, parent);
    }

    @Override
    public Bullet action() {
        if (canDo()){
            return new Sun(this.getX()+0.2,this.getY(),0,0);
        }
        return null;
    }
}