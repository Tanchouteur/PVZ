package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.game.rowComponent.Mower;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MowerView extends ImageView {
    private final Mower mower;

    public MowerView(Mower mower, Image image){
        super(image);

        this.mower = mower;
        this.setFitWidth(130);
        this.setFitHeight(130);
    }

    public void update(){
        this.setLayoutX(mower.getX()*175-60);
        this.setLayoutY((mower.getY()*185)+20);

    }
}
