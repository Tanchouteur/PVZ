package fr.tanchou.pvz.game;

import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.PeaShooter;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.SunFlower;
import fr.tanchou.pvz.game.rowComponent.Row;
import fr.tanchou.pvz.game.spawn.SerieRowFactory;
import fr.tanchou.pvz.game.spawn.ZombieFactory;
import fr.tanchou.pvz.game.spawn.ZombieSpawner;
import fr.tanchou.pvz.player.Player;

public class Partie {
    private final Row[] rows;
    private final Player player;
    private boolean defeated = false;

    private final SunManager sunManager;
    private final ZombieSpawner zombieSpawner;

    public Partie(Player player) {
        this.player = player;
        this.rows = new Row[5];
        sunManager = new SunManager();
        for (int i = 0; i < 5; i++) {
            this.rows[i] = new Row(i, sunManager);
        }

        zombieSpawner = new ZombieSpawner(this, new SerieRowFactory(new ZombieFactory()));

        rows[1].placePlantInCase(0, new PeaShooter(1,1));
        rows[2].placePlantInCase(1, new SunFlower(0,2));

        /*rows[0].addPlant(new PeaShooter(1.0, 0));
        rows[0].addPlant(new PeaShooter(2.0, 0));
        rows[0].addPlant(new PeaShooter(3.0, 0));

        rows[1].addPlant(new PeaShooter(0.0, 1));
        rows[2].addPlant(new PeaShooter(0.0, 2));*/


        /*rows[0].addZombie(new NormalZombie(3.0, 0));*/
        /*rows[1].addZombie(new NormalZombie(8.0, 0));
        rows[2].addZombie(new NormalZombie(8.0, 0));
        rows[3].addZombie(new NormalZombie(8.0, 0));
        rows[4].addZombie(new NormalZombie(8.0, 0));*/
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

    public void update() {

        for (Row row : rows) {
            row.tick();
            sunManager.tick();
            defeated = row.isDefeat();
        }

        zombieSpawner.tick();

        player.tick();
    }

    public boolean isDefeated() {
        return defeated;
    }

    public SunManager getSunManager() {
        return sunManager;
    }

}