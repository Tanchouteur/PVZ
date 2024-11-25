package fr.tanchou.pvz.game;

import fr.tanchou.pvz.entities.plants.Plant;
import fr.tanchou.pvz.entities.plants.shooter.pea.PeaShooter;
import fr.tanchou.pvz.entities.plants.passive.sunflower.SunFlower;
import fr.tanchou.pvz.game.board.Case;
import fr.tanchou.pvz.game.board.Row;
import fr.tanchou.pvz.game.board.SunManager;
import fr.tanchou.pvz.game.spawn.SerieRowFactory;
import fr.tanchou.pvz.game.spawn.ZombieFactory;
import fr.tanchou.pvz.game.spawn.ZombieSpawner;
import fr.tanchou.pvz.player.Player;
import javafx.scene.layout.Pane;

public class Partie {
    private final Row[] rows;
    private final Player player;
    private boolean defeated = false;

    private Pane rootPane;

    private final SunManager sunManager;

    private final ZombieSpawner zombieSpawner;


    public Partie(Player player, Pane rootPane) {
        this.player = player;
        this.rows = new Row[5];
        sunManager = new SunManager();
        for (int i = 0; i < 5; i++) {
            this.rows[i] = new Row(sunManager);
        }

        this.rootPane = rootPane;

        zombieSpawner = new ZombieSpawner(this, new SerieRowFactory(new ZombieFactory()));

        rows[0].placePlantInCase(0, new PeaShooter());
        rows[0].placePlantInCase(1, new SunFlower());

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

            // Mettre à jour les zombies
            row.updateZombies();

            // Mettre à jour les plantes
            row.updatePlants();

            // Mettre à jour les projectiles
            row.updateBullets();

            this.sunManager.updateSuns(player);

        }

        zombieSpawner.tick();
    }

    public boolean isDefeated() {
        return defeated;
    }

    public SunManager getSunManager() {
        return sunManager;
    }

    public Case getCaseUnderMouse(double mouseX, double mouseY, double rowHeight, double caseWidth) {
        // Déterminer l'indice de la ligne (Row) en fonction de la hauteur de chaque ligne
        int rowIndex = (int) (mouseY / rowHeight);
        if (rowIndex < 0 || rowIndex >= rows.length) {
            return null; // Hors des limites
        }

        // Déterminer l'indice de la case (Case) en fonction de la largeur de chaque case
        Row row = rows[rowIndex];
        int caseIndex = (int) (mouseX / caseWidth);
        if (caseIndex < 0 || caseIndex >= row.getCases().size()) {
            return null; // Hors des limites
        }

        // Retourner la case correspondante
        return row.getCases().get(caseIndex);
    }

    public void placePlantUnderMouse(double mouseX, double mouseY) {
        double rowHeight = rootPane.getHeight() * 0.17;
        double caseWidth = rootPane.getWidth() / 9;

        // Identifier la case sous la souris
        Case targetCase = getCaseUnderMouse(mouseX, mouseY, rowHeight, caseWidth);

        if (targetCase != null && targetCase.isOccupied()) {
            // Vérifiez si le joueur a assez de soleil pour planter
            if (player.canAffordPlant(player.getSelectedPlant())) {
                // Placez la plante dans la case
                targetCase.placePlant(player.getSelectedPlant()); // Exemple : Ajouter une plante spécifique
                player.removeSun(player.getSelectedPlant().getCard().getCost()); // Déduire le coût
            }
        }
    }

}
