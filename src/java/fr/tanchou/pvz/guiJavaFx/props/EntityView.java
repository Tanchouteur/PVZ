package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.Entity;

import java.util.Objects;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class EntityView extends ImageView {
    private Entity entity;
    private int lastHealth;

    public EntityView(Image image, double width, double height) {
        super(image);
        this.setFitWidth(width);
        this.setFitHeight(height);
    }

    public abstract void update();

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
