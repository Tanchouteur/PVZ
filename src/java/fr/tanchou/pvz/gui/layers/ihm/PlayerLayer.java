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
        this.setPrefSize(width, height);
        this.setLayoutX(0);
        this.setLayoutY(0);

        this.hudLayer = new HudLayer(width, height, partie.getPlayer());
        this.sunLayer = new SunLayer(width, height, partie.getPlayer().getSunManager());
        this.getChildren().add(hudLayer);
        this.getChildren().add(sunLayer);
        this.player = partie.getPlayer();
    }

    public void update(Player player) {
        hudLayer.update();
        sunLayer.update();
    }
}
