package fr.tanchou.pvz.game.spawn;

import fr.tanchou.pvz.entities.zombie.Zombie;
import fr.tanchou.pvz.game.Partie;

import java.util.List;
import java.util.Random;

public class ZombieSpawner {

    private Partie partie;  // Référence à la partie du jeu
    private final SerieRowFactory serieRowFactory;  // Pour créer des séries de zombies
    private Random rand;  // Pour gérer la randomisation des zombies
    private int tickCount;  // Compteur de ticks

    public ZombieSpawner(Partie partie, SerieRowFactory serieRowFactory) {
        this.partie = partie;
        this.serieRowFactory = serieRowFactory;
        this.rand = new Random();
        this.tickCount = 0;
    }

    // Gestion du spawn des zombies à chaque tick
    public void tick() {
        tickCount++;

        // Définir un intervalle pour le spawn des zombies, par exemple tous les 3 ticks
        if (tickCount % 100 == 0) {
            // Choisir aléatoirement une ligne sur laquelle spawn un zombie
            int rowIndex = rand.nextInt(5);  // On a 5 lignes possibles
            int zombiesToSpawn = 1;  // Nombre de zombies à spawn par tick (peut être ajusté)

            // Créer une ligne de zombies pour cette itération
            List<Zombie> zombies = serieRowFactory.createZombieRow(rowIndex, zombiesToSpawn);

            // Ajouter ces zombies à la partie du jeu
            for (Zombie zombie : zombies) {
                partie.getOneRow(rowIndex).addZombie(zombie);  // Méthode d'ajout du zombie à la partie
            }
        }
    }
}
