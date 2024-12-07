package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.game.rowComponent.Mower;
import fr.tanchou.pvz.guiJavaFx.sound.SoundManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MowerView extends ImageView {
    private final Mower mower;
    private boolean used = false;
    private final SoundManager soundManager;

    public MowerView(Mower mower, Image image, SoundManager soundManager) {
        super(image);
        this.soundManager = soundManager;
        this.mower = mower;
        this.setFitWidth(130);
        this.setFitHeight(130);
        this.setLayoutX(mower.getX() * 175 - 60);
        this.setLayoutY((mower.getY() * 185) + 20);
    }

    public void update(){
        if (mower.isActive()) {
            this.setLayoutX(mower.getX() * 175 - 60);
            this.setLayoutY((mower.getY() * 185) + 20);
            if (!used) {
                soundManager.playSound("mower");
                used = true;
            }
        }
    }
}
