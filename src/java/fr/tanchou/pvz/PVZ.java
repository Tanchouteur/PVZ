package fr.tanchou.pvz;

import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.PartieController;
import fr.tanchou.pvz.game.SunManager;

public class PVZ {
    private final Player player;

    private Partie partie;
    private PartieController gameController;
    private SunManager sunManager;

    public PVZ(Player player) {
        this.player = player;
    }

    public void createPartie(boolean consoleLog){
        this.sunManager = new SunManager();
        this.partie = new Partie(player, sunManager, consoleLog);
        this.gameController = new PartieController(partie);

        player.setPartie(partie);
        player.setSunManager(sunManager);
    }

    public void startGame(boolean consoleLog) {
        this.createPartie(consoleLog);
        gameController.startGame();
    }

    public void stopGame() {
        if (gameController != null) {
            gameController.stopGame();
        }
    }

    public Partie getPartie() {
        if (partie == null) {
            throw new IllegalStateException("Partie not created");
        }
        return partie;
    }

    public Player getPlayerController() {
        return player;
    }

    public SunManager getSunManager() {
        return sunManager;
    }

    public Player getPlayer() {
        return player;
    }
}
