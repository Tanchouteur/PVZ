package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.Entity;

import java.util.Objects;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class EntityView extends ImageView {
    private Entity entity;
    private int lastHealth;
    int hitEffect = 0;

    public EntityView(Image image, double width, double height) {
        super(image);
        this.setFitWidth(width);
        this.setFitHeight(height);
    }

    public void update(){
        individualUpdate();
        if (getLastHealth()>getEntity().getHealthPoint()){
            this.hitEffect = 10;
            this.setLastHealth(getEntity().getHealthPoint());
        }

        if (hitEffect > 0){
            this.hitEffect--;
        }

        if (hitEffect > 0 && this.getEffect() == null){
            this.setEffect(new ColorAdjust(0,0,0.5, 0));
        }else if (hitEffect <= 0 && this.getEffect() != null){
            this.setEffect(null);
        }
    }

    public abstract void individualUpdate();

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public int getLastHealth() {
        return lastHealth;
    }

    public void setLastHealth(int lastHealth) {
        this.lastHealth = lastHealth;
    }
}
