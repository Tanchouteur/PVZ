package fr.tanchou.pvz.game.spawn;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.zombie.NormalZombie;
import fr.tanchou.pvz.game.Partie;

import java.util.Random;

public class ZombieSpawner {

    private final Partie partie;
    private final Random rand;  // Pour gérer la randomisation des zombies
    private int tickCount;  // Compteur de ticks

    private final Zombie[] zombiesArray;  // Liste des zombies à spawn

    public ZombieSpawner(Partie partie) {
        this.partie = partie;
        this.rand = new Random();
        this.tickCount = 0;
        this.zombiesArray = new Zombie[6];

        // Ajouter les zombies à la liste
        zombiesArray[0] = new NormalZombie(11.0,0);
       zombiesArray[0].setHeating(true);

        partie.getOneRow(1).addZombie(zombiesArray[0].clone(10.0,1));
    }

    public void tick() {
        tickCount++;

        if (tickCount > 50) {
            tickCount = 0;
            // Choisir aléatoirement une ligne sur laquelle spawn un zombie
            int rowIndex = rand.nextInt(5);  // On a 5 lignes possibles

            partie.getOneRow(rowIndex).addZombie(zombiesArray[0].clone(10.0, rowIndex));  // Ajouter le zombie à la ligne
            System.out.println(partie.getOneRow(rowIndex).getListZombies().getLast() + " Spawn in : " + rowIndex);
        }
    }
}
