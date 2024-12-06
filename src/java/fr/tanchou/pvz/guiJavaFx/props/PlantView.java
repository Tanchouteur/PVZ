package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import javafx.scene.image.Image;

import java.util.Objects;

public class PlantView extends EntityView {


    public PlantView(Plant entity, double width, double height) {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        Image image = new Image(Objects.requireNonNull(entity.getClass().getResourceAsStream("/assets/plants/"+ entity.getName() +".gif")));

        super(image, width, height);
        this.setEntity(entity);

        this.setLastHealth(this.getEntity().getHealthPoint());
    }

    public void individualUpdate() {
        /*this.setLayoutX((this.getEntity().getX()*168) - 100);
        this.setLayoutY((this.getEntity().getY()*178) - 155);*/
    }
}
