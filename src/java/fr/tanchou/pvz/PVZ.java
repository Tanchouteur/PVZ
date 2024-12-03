package fr.tanchou.pvz;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.PartieController;
import fr.tanchou.pvz.game.SunManager;

public class PVZ {
    private final PlayerController playerController;

    private Partie partie;
    private PartieController gameController;
    private SunManager sunManager;

    public PVZ() {

        this.playerController = new PlayerController("Louis");

        createPartie();
    }

    public void createPartie(){
        this.sunManager = new SunManager();
        this.partie = new Partie(playerController, sunManager);
        this.gameController = new PartieController(partie);

        playerController.setPartie(partie);
        playerController.setSunManager(sunManager);
    }

    public void startGame() {
        gameController.startGame();
    }

    public void stopGame() {
        gameController.stopGame();
    }
}
