package fr.tanchou.pvz.game;

import fr.tanchou.pvz.ia.network.GameAI;

public class PVZ {
    private final Player player;

    private Partie partie;
    private PartieController gameController;

    private GameAI gameAI;

    public PVZ(Player player) {
        this.player = player;
    }

    public PVZ(Player player, GameAI gameAI) {
        this.player = player;

        this.gameAI = gameAI;
    }

    public void startGame(boolean consoleLog) {
        this.createPartie(consoleLog);
        gameController.startGame();
    }

    public void createPartie(boolean consoleLog) {
        SunManager sunManager = new SunManager();
        this.partie = new Partie(player, sunManager, consoleLog);
        this.gameController = new PartieController(partie, gameAI);

        player.setPartie(partie);
        player.setSunManager(sunManager);
    }

    public void runManualGame() {
        createPartie(false);
        while (!partie.isDefeated() && !partie.isVictory()) {

            gameController.update();
            //System.out.println("Tick: " + gameController.getTickCount());
        }

        if (partie.isVictory()) {
            System.err.println("Victory!");
        }
        this.gameAI.getNeuralNetwork().setScore((int) Math.round(player.calculateScore()));

        if (player.calculateScore() <= 0) {
            System.err.println("Score p: " + player.calculateScore() + " Score IA: " + this.gameAI.getNeuralNetwork().getScore());
        }

        //System.out.println("Score p: " + player.calculateScore() + " Score IA: " + this.gameAI.getNeuralNetwork().getScore());
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
}
