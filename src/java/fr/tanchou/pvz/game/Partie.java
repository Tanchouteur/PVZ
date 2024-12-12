package fr.tanchou.pvz.game;

import fr.tanchou.pvz.Player;
import fr.tanchou.pvz.game.rowComponent.Row;
import fr.tanchou.pvz.game.spawn.ZombieSpawner;

public class Partie {
    private final Row[] rows;
    private final Player player;
    private boolean defeated = false;
    private boolean victory = false;

    private final SunManager sunManager;
    private final ZombieSpawner zombieSpawner;

    private int tick = 0;

    private final boolean consoleLog;

    public Partie(Player player, SunManager sunManager, boolean consoleLog) {
        this.player = player;
        this.rows = new Row[5];
        this.sunManager = sunManager;
        this.consoleLog = consoleLog;

        for (int i = 0; i < 5; i++) {
            this.rows[i] = new Row(i, sunManager);
        }

        zombieSpawner = new ZombieSpawner(this);
        /*WallNut wallNut = new WallNut(0, 1);
        wallNut.takeDamage(170);
        rows[1].placePlantInCase(wallNut);*/


        /*rows[1].placePlantInCase(new FreezePeaShooter(0,1));
        rows[2].placePlantInCase(new SunFlower(1,2));*/

        /*zombieSpawner.spawnZombie(4);*/
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
        zombieSpawner.tick();
        player.tick();
    }

    public void consoleLog(){
        this.tick++;
        if (tick >= 12) {
            System.out.println(this);
            tick = 0;
        }
    }

    public Row[] getRows() {
        return rows;
    }

    public Player getPlayer() {
        return player;
    }

    public Row getOneRow(int index) {
        return rows[index];
    }

    public boolean isDefeated() {
        return defeated;
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

    public ZombieSpawner getZombieSpawner() {
        return zombieSpawner;
    }

    public SunManager getSunManager() {
        return sunManager;
    }
}