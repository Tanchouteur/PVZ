package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.entityRealisation.plants.passive.WallNut;
import fr.tanchou.pvz.guiJavaFx.assetsLoder.AssetsLoader;
import javafx.scene.image.Image;

import java.util.Map;

public class PlantView extends EntityView {
    private final Map<String, Image> assets;
    private Image imageUsed;
    private boolean dammagedAssets = false;

    public PlantView(Plant entity, double width, double height) {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }

        super(AssetsLoader.getAssetEntity(entity, entity.getName()).get("normal"), width, height);
        this.setEntity(entity);

        assets = AssetsLoader.getAssetEntity(entity, entity.getName());
        this.imageUsed = assets.get("normal");
        this.setLastHealth(this.getEntity().getHealthPoint());

        if (entity instanceof WallNut){
            this.dammagedAssets = true;
        }
    }

    public void individualUpdate() {
        if (dammagedAssets){
            if (getEntity().getHealthPoint() < (getEntity().getHealthPoint()/3)*2 && this.getImage() != assets.get("damaged-1")){
                this.imageUsed = assets.get("damaged-1");
                this.setImage(imageUsed);
            }else if (getEntity().getHealthPoint() < (getEntity().getHealthPoint()/3)  && this.getImage() != assets.get("damaged-2")){
                this.imageUsed = assets.get("damaged-2");
                this.setImage(imageUsed);
            }else if (getEntity().getHealthPoint() >= (getEntity().getHealthPoint()/3)*2 && this.getImage() != assets.get("normal")){
                this.imageUsed = assets.get("normal");
                this.setImage(imageUsed);
            }
        }
    }
}
