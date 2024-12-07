package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.effect.FreezeEffect;
import fr.tanchou.pvz.guiJavaFx.assetsLoder.AssetsLoader;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;

import java.util.Map;

public class ZombieView extends EntityView {
    private final Image heatingAnimation;
    private final Image walkAnimation;

    public ZombieView(Zombie entity, double width, double height, Map<String, Image> assets) {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }

        super(assets.get("Zombie-walk"), width, height);

        this.walkAnimation = assets.get("Zombie-walk");
        this.heatingAnimation = assets.get("Zombie-heating");
        this.setEntity(entity);

        this.setLastHealth(this.getEntity().getHealthPoint());
    }

    @Override
    public void individualUpdate() {
        this.setLayoutX((this.getEntity().getX()*168) - 80);
        this.setLayoutY((this.getEntity().getY()*178) - 80);

        if (((Zombie) this.getEntity()).heating()){
            this.setImage(heatingAnimation);
            this.setFitWidth(getFitWidth());
            this.setFitHeight(getFitHeight());
        }else {
            this.setImage(walkAnimation);
            this.setFitWidth(getFitWidth());
            this.setFitHeight(getFitHeight());
        }

        if (((Zombie) this.getEntity()).getEffect() instanceof FreezeEffect){//Si le zombie est gelé
            //rendre le zombie bleu
            this.setEffect(new ColorAdjust(0.5, 0.5, 0.2, 0));
        }else {
            this.setEffect(null);
        }
    }
}
