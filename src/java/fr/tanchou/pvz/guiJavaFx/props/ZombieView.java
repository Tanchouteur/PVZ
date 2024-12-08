package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.effect.FreezeEffect;
import fr.tanchou.pvz.guiJavaFx.sound.SoundManager;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;

import java.util.Map;

public class ZombieView extends EntityView {
    private final Image heatingAnimation;
    private final Image walkAnimation;
    private final Image walkDamagedAnimation;

    public ZombieView(Zombie entity, double width, double height, Map<String, Image> assets, SoundManager soundManager) {
        super(assets.get("Zombie-walk"), width, height, soundManager);

        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }

        this.walkAnimation = assets.get("Zombie-walk");
        this.heatingAnimation = assets.get("Zombie-heating");
        this.walkDamagedAnimation = assets.get("Zombie-walk-damaged");
        this.setEntity(entity);

        this.setLastHealth(this.getEntity().getHealthPoint());
    }

    @Override
    public void individualUpdate() {

        this.setLayoutX((this.getEntity().getX()*168) - 80);
        this.setLayoutY((this.getEntity().getY()*178) - 80);

        if (((Zombie) this.getEntity()).heating() && this.getImage() != heatingAnimation){
            this.setImage(heatingAnimation);
        }else if (!((Zombie) this.getEntity()).heating()){
            if (this.getEntity().getHealthPoint() <= 100 && this.getImage() != walkDamagedAnimation) {
                this.setImage(walkDamagedAnimation);
            }else if (this.getImage() != walkAnimation && this.getEntity().getHealthPoint() > 100){
                this.setImage(walkAnimation);
            }
        }

        if (((Zombie) this.getEntity()).getEffect() instanceof FreezeEffect){//Si le zombie est gelé
            //rendre le zombie bleu
            this.setEffect(new ColorAdjust(0.5, 0.5, 0.2, 0));
        }else {
            this.setEffect(null);
        }
    }
}
