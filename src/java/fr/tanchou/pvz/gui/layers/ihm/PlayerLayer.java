package fr.tanchou.pvz.gui.layers.ihm;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.gui.layers.game.SunLayer;
import javafx.scene.layout.Pane;

public class PlayerLayer extends Pane {
    private final HudLayer hudLayer;
    private final SunLayer sunLayer;
    private final Player player;

    public PlayerLayer(int width, int height, Partie partie) {
        super();
        this.setPrefSize(width*0.05, height*0.56);
        this.setLayoutX(width*0.00625);
        this.setLayoutY(((double) height /2) - this.getPrefHeight()/2);
        //this.setPrefSize(width, height);
        /*this.setLayoutX(0);
        this.setLayoutY(0);*/

        this.hudLayer = new HudLayer( partie.getPlayer());
        this.sunLayer = new SunLayer(width, height, partie.getPlayer().getSunManager());

        this.getChildren().add(sunLayer);
        this.getChildren().add(hudLayer);
        this.player = partie.getPlayer();

        sunLayer.setMouseTransparent(true);
        hudLayer.setMouseTransparent(false);
    }

    public void update() {
        hudLayer.update();
        sunLayer.update();
    }
}
