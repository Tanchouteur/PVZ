package fr.tanchou.pvz.game;

import fr.tanchou.pvz.entities.plants.pea.PeaShooter;
import fr.tanchou.pvz.entities.zombie.normalZombie.NormalZombie;
import fr.tanchou.pvz.player.Player;

public class Partie {
    private final Row[] rows;
    private final Player player;
    private boolean defeated = false;

    public Partie(Player player) {
        this.player = player;
        this.rows = new Row[5];
        for (int i = 0; i < 5; i++) {
            this.rows[i] = new Row();
        }

        rows[0].addPlant(new PeaShooter(0.0, 0));
        rows[1].addPlant(new PeaShooter(0.0, 1));
        rows[2].addPlant(new PeaShooter(0.0, 2));


        rows[0].addZombie(new NormalZombie(2.0, 0));
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

            // Mettre à jour les zombies
            row.updateZombies();

            // Mettre à jour les plantes
            row.updatePlants();

            // Mettre à jour les projectiles
            row.updateBullets();
        }

        // Ajouter de nouveaux zombies (si nécessaire)
        spawnZombies();
    }


    private void spawnZombies() {
    }

    public boolean isDefeated() {
        return defeated;
    }
}
