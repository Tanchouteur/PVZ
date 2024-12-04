package fr.tanchou.pvz.gui.props;

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

        this.setFitWidth(100);
        this.setFitHeight(100);

        this.setMouseTransparent(false);

    }

    public void updateSunPosition() {
        this.setLayoutX((sun.getX() * 100)+280);
        this.setLayoutY(sun.getY()*100);

        System.err.println(this.getLayoutX() + "  " + this.getLayoutY());
    }

    public Sun getSun() {
        return sun;
    }
}
