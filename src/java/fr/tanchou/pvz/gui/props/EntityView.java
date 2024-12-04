package fr.tanchou.pvz.gui.props;

import fr.tanchou.pvz.abstractEnity.Entity;

import java.util.Objects;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EntityView extends ImageView {
    private final Entity entity;
    private Image heatingAnimation;
    private final Image walkAnimation;
    private boolean heating = false;

    public EntityView(Entity entity, double width, double height) {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        Image image;
        if (entity instanceof Zombie){
            image = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/zombies/"+entity.getName()+"/"+ entity.getName() +".png")));
        }else {
            image = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/plants/"+ entity.getName() +".gif")));
        }
        this.walkAnimation = image;
        super(image);
        this.entity = entity;

        this.setFitWidth(width);
        this.setFitHeight(height);

        //this.heatingAnimation = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/zombies/"+entity.getName()+"/ZombieAttack.gif")));
    }

    public void updateZombiePosition() {
        this.setLayoutX((entity.getX()*170) - 100);
        this.setLayoutY((entity.getY()*178) - 75);
        /*if ((entity instanceof Zombie zombie && zombie.heating())&&!this.heating){
            this.heating = true;
            this.setImage(heatingAnimation);
        }else if ((entity instanceof Zombie zombie && !zombie.heating())&&this.heating){
            this.heating = false;
            this.setImage(walkAnimation);
        }*/
    }

    public Entity getEntity() {
        return entity;
    }
}
