package fr.tanchou.pvz.game;

import fr.tanchou.pvz.entities.Bullet;
import fr.tanchou.pvz.entities.plants.Plant;
import fr.tanchou.pvz.entities.plants.pea.PeaShooter;
import fr.tanchou.pvz.entities.zombie.Zombie;
import fr.tanchou.pvz.player.Player;

public class Partie {
    private final Row[] rows;
    private final Player player;

    public Partie(Player player) {
        this.player = player;
        this.rows = new Row[5];
        for (int i = 0; i < 5; i++) {
            this.rows[i] = new Row();
        }

        rows[0].addPlant(new PeaShooter(0.0, 0));
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
            for (Zombie zombie : row.getListZombies()) {
                zombie.move(); // Avancer les zombies

                // Vérifier si le zombie atteint la fin de la rangée
                if (zombie.getX() < 0) {

                    System.out.println("Game Over! Un zombie a atteint la maison.");
                    return;
                }
            }

            // Mettre à jour les plantes
            for (Plant plant : row.getListPlants()) {
                plant.tick(); // Mettre à jour le compteur de tir
                Bullet bullet = plant.shoot(); // Faire tirer la plante

                if (bullet != null) {
                    row.addBullet(bullet); // Ajouter le projectile à la rangée
                }
            }

            // Mettre à jour les projectiles
            row.updateBullets();

        }

        // Ajouter de nouveaux zombies (si nécessaire)
        spawnZombies();
    }


    private void spawnZombies() {
    }

}
