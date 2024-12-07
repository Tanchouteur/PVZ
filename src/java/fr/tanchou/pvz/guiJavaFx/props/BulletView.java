package fr.tanchou.pvz.guiJavaFx.props;

import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.Bullet;

import fr.tanchou.pvz.guiJavaFx.assetsLoder.AssetsLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class BulletView extends ImageView {
    private final Bullet bullet;

    public BulletView(Bullet bullet, Image asset) {
        if (bullet == null) {
            throw new IllegalArgumentException("bullet cannot be null");
        }

        super(asset);
        this.setFitWidth(50);
        this.setFitHeight(50);
        this.setPreserveRatio(true);
        this.bullet = bullet;

    }

    public void updateBulletPosition() {
        this.setLayoutX(bullet.getX() * 150 + 10);
        this.setLayoutY(bullet.getY()*185 - 10);
        //System.out.println("Bullet position: X=" + this.getLayoutX() + ", Y=" + this.getLayoutY());
    }

    public Bullet getBullet() {
        return bullet;
    }
}
