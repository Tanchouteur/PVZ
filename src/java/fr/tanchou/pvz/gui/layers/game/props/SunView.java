package fr.tanchou.pvz.gui.layers.game.props;

import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class SunView extends ImageView {
    private final Sun sun;

    public SunView(Sun sun) {
        if (sun == null) {
            throw new IllegalArgumentException("sun cannot be null");
        }
        this.sun = sun;

        Image image = new Image(Objects.requireNonNull(sun.getClass().getResourceAsStream("/assets/items/Sun/SunAnimated.gif")));
        super(image);

        this.setFitWidth(50);
        this.setFitHeight(50);
    }

    public void updateSunPosition() {
        this.setLayoutX(sun.getX() * 150 + 10);
        this.setLayoutY(sun.getY()*190 - 10);
    }

    public Sun getSun() {
        return sun;
    }
}
