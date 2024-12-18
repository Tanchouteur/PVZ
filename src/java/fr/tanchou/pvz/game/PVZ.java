package fr.tanchou.pvz.game;

import fr.tanchou.pvz.ia.network.GameAI;

public class PVZ {
    private final Player player;

    private Partie partie;
    private PartieController gameController;
    private double multiplicateurOfSpeed = 1;

    private GameAI gameAI;
    private boolean random = false;

    public PVZ(Player player, boolean random) {
        this.player = player;
        this.random = random;
    }

    public PVZ(Player player, GameAI gameAI, boolean random) {
        this.player = player;
        this.gameAI = gameAI;
        this.random = random;
    }

    public PVZ(Player player, GameAI gameAI, double multiplicateurOfSpeed, boolean random) {
        this.player = player;
        this.gameAI = gameAI;
        this.random = random;
        this.multiplicateurOfSpeed = multiplicateurOfSpeed;
    }

    public void startGame(boolean consoleLog) {
        this.createPartie(consoleLog, random);
        gameController.startGame(multiplicateurOfSpeed);
    }

    public void createPartie(boolean consoleLog, boolean random) {
        SunManager sunManager = new SunManager();
        this.partie = new Partie(player, sunManager, consoleLog, random);
        this.gameController = new PartieController(partie, gameAI);

        player.setPartie(partie);
        player.setSunManager(sunManager);
    }

    public void runManualGame() {
        createPartie(false, random);
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

    public PartieController getGameController() {
        return gameController;
    }
}
