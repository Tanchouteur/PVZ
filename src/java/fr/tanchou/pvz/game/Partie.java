package fr.tanchou.pvz.game;

import fr.tanchou.pvz.PlayerController;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.PeaShooter;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.SunFlower;
import fr.tanchou.pvz.game.rowComponent.Row;
import fr.tanchou.pvz.game.spawn.ZombieSpawner;

public class Partie {
    private final Row[] rows;
    private final PlayerController playerController;
    private boolean defeated = false;
    private boolean victory = false;

    private final SunManager sunManager;
    private final ZombieSpawner zombieSpawner;

    private int tick = 0;

    private boolean consoleLog;

    public Partie(PlayerController playerController, SunManager sunManager, boolean consoleLog) {
        this.playerController = playerController;
        this.rows = new Row[5];
        this.sunManager = sunManager;
        this.consoleLog = consoleLog;

        for (int i = 0; i < 5; i++) {
            this.rows[i] = new Row(i, sunManager);
        }

        zombieSpawner = new ZombieSpawner(this);

        rows[1].placePlantInCase(0, new PeaShooter(0,1));
        rows[2].placePlantInCase(1, new SunFlower(1,2));

        zombieSpawner.spawnZombie(2);
    }

    public Row[] getRows() {
        return rows;
    }

    public PlayerController getPlayer() {
        return playerController;
    }

    public Row getOneRow(int index) {
        return rows[index];
    }

    public void update() {
        if (consoleLog) {
            consoleLog();
        }

        for (Row row : rows) {
            row.tick();
            if (row.isDefeat()){
                defeated = true;
            }
        }

        sunManager.tick();
        //zombieSpawner.tick();

        playerController.tick();
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void consoleLog(){
        this.tick++;
        if (tick >= 12) {
            System.out.println(this);
            tick = 0;
        }
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Partie : defeat=").append(this.isDefeated()).append("\n");
        for (Row row : rows) {
            stringBuilder.append(row).append("\n");
            stringBuilder.append("----------------------------------------------------------------------------------------------------------").append("\n");
        }
        return stringBuilder.toString();
    }

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }
}