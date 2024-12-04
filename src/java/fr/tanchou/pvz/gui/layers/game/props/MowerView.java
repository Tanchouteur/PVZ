package fr.tanchou.pvz.gui.layers.game.props;

import fr.tanchou.pvz.game.rowComponent.Mower;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class MowerView extends ImageView {
    private final Mower mower;

    public MowerView(Mower mower){
        Image image = new Image(Objects.requireNonNull(mower.getClass().getResourceAsStream("/assets/items/Mower.png")));
        super(image);

        this.mower = mower;
        this.setFitWidth(100);
        this.setFitHeight(100);

        update();
    }

    public void update(){
        this.setLayoutX(mower.getX()-100);
        this.setLayoutY((mower.getY()*185)+20);
    }

    public Mower getMower() {
        return mower;
    }
}
