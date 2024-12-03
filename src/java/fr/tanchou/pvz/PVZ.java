package fr.tanchou.pvz;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.PartieController;
import fr.tanchou.pvz.game.SunManager;

public class PVZ {
    private final Player player;
    private final Partie partie;
    private final PartieController gameController;

    private final SunManager sunManager;

    public PVZ() {
        this.sunManager = new SunManager();
        this.player = new Player("Louis" , sunManager);
        this.partie = new Partie(player, sunManager);
        this.gameController = new PartieController(partie);
    }

    public void startGame() {
        gameController.startGame();
    }

    public void stopGame() {
        gameController.stopGame();
    }

    public static void main(String[] args) {
        PVZ pvz = new PVZ();
        pvz.startGame();
    }
}
