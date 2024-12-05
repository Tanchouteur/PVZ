package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.Entity;
import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class PlantView extends EntityView {

    public PlantView(Plant entity, double width, double height) {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        Image image = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/plants/"+ entity.getName() +".gif")));

        super(image, width, height);
        this.setEntity(entity);
    }

    public void update() {
        /*this.setLayoutX((this.getEntity().getX()*168) - 100);
        this.setLayoutY((this.getEntity().getY()*178) - 155);*/
    }
}
