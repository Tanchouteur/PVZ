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

    public void startGame(boolean consoleLog, boolean ia) {
        this.createPartie(consoleLog, ia);
        gameController.startGame();
    }

    public void createPartie(boolean consoleLog, boolean ia) {
        this.sunManager = new SunManager();
        this.partie = new Partie(player, sunManager, consoleLog);
        this.gameController = new PartieController(partie, ia);

        player.setPartie(partie);
        player.setSunManager(sunManager);
    }

    public void runManualGame(int maxTicks, boolean iaEnabled) {
        createPartie(false, iaEnabled);
        while (gameController.getTickCount() < maxTicks &&
                !partie.isDefeated() &&
                !partie.isVictory()) {
            gameController.update();
        }

        if (partie.isVictory()) {
            //System.out.println("Victory!");
        } else {
            //System.out.println("Game Over.");
        }
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

    public Player getPlayer() {
        return player;
    }

    public PartieController getPartieController() {
        return gameController;
    }
}
