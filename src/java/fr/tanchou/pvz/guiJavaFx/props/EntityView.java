package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.Entity;

import fr.tanchou.pvz.guiJavaFx.sound.SoundManager;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class EntityView extends ImageView {
    private Entity entity;
    private int lastHealth;
    int hitEffect = 0;
    private boolean hitSoundUsable = true;

    protected final SoundManager soundManager;

    public EntityView(Image image, double width, double height, SoundManager soundManager) {
        super(image);
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.soundManager = soundManager;
    }

    public void update(){
        individualUpdate();
        if (getLastHealth()>getEntity().getHealthPoint()){
            this.hitEffect = 20;
            this.setLastHealth(getEntity().getHealthPoint());
        }

        if (hitEffect > 0){
            this.hitEffect--;
            if (hitSoundUsable){
                hitSoundUsable = false;
            }
        }else {
            if (!hitSoundUsable){
                hitSoundUsable = true;
            }
        }

        if (hitSoundUsable){
            if (this instanceof PlantView){
                soundManager.playSound("zombieBit");
            }else {
                soundManager.playSound("splat");
            }
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
