package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.SunFlower;
import fr.tanchou.pvz.entityRealisation.plants.passive.WallNut;
import javafx.scene.image.Image;

import java.util.Map;

public class PlantView extends EntityView {
    private final Map<String, Image> assets;
    private Image imageUsed;
    private boolean dammagedAssets = false;

    public PlantView(Plant entity, double width, double height, Map<String, Image> assetsLoaded) {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }

        super(assetsLoaded.get("normal"), width, height);
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
            if (getEntity().getHealthPoint() < (this.getEntity().getOriginalHealth()/3)*2 && this.imageUsed != assets.get("damaged-1")){
                this.imageUsed = assets.get("damaged-1");
                this.setImage(imageUsed);
            }else if (getEntity().getHealthPoint() < (this.getEntity().getOriginalHealth()/3)  && this.imageUsed != assets.get("damaged-2")){
                this.imageUsed = assets.get("damaged-2");
                this.setImage(imageUsed);
            }else if (getEntity().getHealthPoint() >= (this.getEntity().getOriginalHealth()/3)*2 && this.imageUsed != assets.get("normal")){
                this.imageUsed = assets.get("normal");
                this.setImage(imageUsed);
            }
        }else if (getEntity() instanceof SunFlower sunFlower && sunFlower.getTimeSinceLastFire() > (sunFlower.getFireRate()/5)*4){
            this.hitEffect = 35;
        }
    }
}
