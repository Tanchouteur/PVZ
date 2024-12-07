package fr.tanchou.pvz.guiJavaFx.layers.ihm;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.guiJavaFx.assetsLoder.AssetsLoader;
import fr.tanchou.pvz.guiJavaFx.layers.game.SunLayer;
import fr.tanchou.pvz.guiJavaFx.sound.SoundManager;
import javafx.scene.layout.Pane;

public class PlayerLayer extends Pane {
    private final HudLayer hudLayer;
    private final SunLayer sunLayer;

    public PlayerLayer(int width, int height, Partie partie, AssetsLoader assetsLoader) {
        super();
        this.setPrefSize(width*0.05, height*0.6);
        this.setLayoutX(width*0.00625);
        this.setLayoutY(((double) height /2) - this.getPrefHeight()/2);
        //this.setPrefSize(width, height);
        /*this.setLayoutX(0);
        this.setLayoutY(0);*/

        this.hudLayer = new HudLayer(partie.getPlayer(), width, height*0.61);
        this.sunLayer = new SunLayer(width, height, partie.getPlayer().getSunManager(), assetsLoader.getAssetsItems("sun").get("normal"));

        this.getChildren().add(sunLayer);
        this.getChildren().add(hudLayer);

        sunLayer.setMouseTransparent(true);
        hudLayer.setMouseTransparent(false);
    }

    public void update() {
        hudLayer.update();
        sunLayer.update();
    }
}
