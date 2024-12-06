package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import fr.tanchou.pvz.guiJavaFx.assetsLoder.AssetsLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class SunView extends ImageView {
    private final Sun sun;

    public SunView(Sun sun, Image asset) {
        super(asset);
        if (sun == null) {
            throw new IllegalArgumentException("sun cannot be null");
        }
        this.sun = sun;



        this.setFitWidth(100);
        this.setFitHeight(100);

        this.setLayoutX((sun.getX() * 100)+280);
        this.setLayoutY(sun.getY()*100);

        this.setMouseTransparent(false);
        this.toFront();

    }

    public void updateSunPosition() {

        //this.toFront();

        /*this.setLayoutX((sun.getX() * 100)+280);
        this.setLayoutY(sun.getY()*100);*/

        //System.err.println(this.getLayoutX() + "  " + this.getLayoutY());
    }

    public Sun getSun() {
        return sun;
    }
}
