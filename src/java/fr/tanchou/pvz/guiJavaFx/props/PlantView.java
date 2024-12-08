package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.SunFlower;
import fr.tanchou.pvz.entityRealisation.plants.PotatoMine;
import fr.tanchou.pvz.entityRealisation.plants.passive.WallNut;
import fr.tanchou.pvz.guiJavaFx.sound.SoundManager;
import javafx.scene.image.Image;

import java.util.Map;

public class PlantView extends EntityView {
    private final Map<String, Image> assets;
    private Image imageUsed;
    private boolean dammagedAssets = false;

    public PlantView(Plant entity, double width, double height, Map<String, Image> assetsLoaded, SoundManager soundManager) {
        super(assetsLoaded.get("normal"), width, height, soundManager);
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }

        this.setEntity(entity);

        assets = assetsLoaded;
        this.imageUsed = assets.get("normal");
        this.setLastHealth(this.getEntity().getHealthPoint());

        if (entity instanceof WallNut){
            this.dammagedAssets = true;
        }
    }

    public void individualUpdate() {
        if (dammagedAssets){
            if (getEntity().getHealthPoint() < (this.getEntity().getOriginalHealth()/3)*2){
                this.imageUsed = assets.get("damaged-1");
            }else if (getEntity().getHealthPoint() < (this.getEntity().getOriginalHealth()/3)){
                this.imageUsed = assets.get("damaged-2");
            }else if (getEntity().getHealthPoint() >= (this.getEntity().getOriginalHealth()/3)*2){
                this.imageUsed = assets.get("normal");
            }
        }else if (getEntity() instanceof SunFlower sunFlower && sunFlower.getTimeSinceLastFire() > (sunFlower.getFireRate()/10)*9){
            this.hitEffect = 5;
        } else if (getEntity() instanceof PotatoMine potatoMine && potatoMine.getUsable() <= 0){
            this.imageUsed = assets.get("ready");
        }else if (getEntity() instanceof PotatoMine) {
            this.imageUsed = assets.get("normal");
        }
        if (this.imageUsed != this.getImage()){
            this.setImage(imageUsed);
        }
    }
}
