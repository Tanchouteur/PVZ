package fr.tanchou.pvz.gui.props;

import fr.tanchou.pvz.abstractEnity.Entity;

import java.util.Objects;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EntityView extends ImageView {
    private final Entity entity;

    public EntityView(Entity entity, double width, double height) {
        Image image;
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }

        if (entity instanceof Zombie){
            image = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/zombies/" + entity.getName() + "/"+ entity.getName() +".png")));
        }else {
            image = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/plants/" + entity.getName() + "/"+ entity.getName() +".gif")));
        }


        super(image);
        this.entity = entity;
        this.setLayoutX(0);
        this.setLayoutY(0);

        this.setFitWidth(width);
        this.setFitHeight(height);
    }

    public void update() {
        this.setLayoutX(0);
        this.setLayoutY(0);
    }
}
