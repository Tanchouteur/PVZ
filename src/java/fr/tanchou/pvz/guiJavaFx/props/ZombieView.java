package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.effect.FreezeEffect;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;

import java.util.Objects;

public class ZombieView extends EntityView {
    private final Image heatingAnimation;
    private final Image walkAnimation;

    public ZombieView(Zombie entity, double width, double height) {
        Image image = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/zombies/"+entity.getName()+"Zombie/"+ entity.getName() +"Zombie-walk.gif")));
        super(image, width, height);
        this.setEntity(entity);
        this.walkAnimation = image;
        this.heatingAnimation = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/zombies/"+entity.getName()+"Zombie/"+ entity.getName() +"Zombie-heating.gif")));

        this.setLastHealth(this.getEntity().getHealthPoint());
    }

    @Override
    public void individualUpdate() {
        this.setLayoutX((this.getEntity().getX()*168) - 100);
        this.setLayoutY((this.getEntity().getY()*178) - 155);

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
