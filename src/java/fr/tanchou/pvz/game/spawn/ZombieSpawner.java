package fr.tanchou.pvz.game.spawn;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.zombie.NormalZombie;
import fr.tanchou.pvz.game.Partie;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ZombieSpawner {

    private final Partie partie;
    private final Random rand;  // Pour gérer la randomisation des zombies
    private int tickCount;  // Compteur de ticks

    private final List<Zombie> zombies;  // Liste des zombies à spawn

    public ZombieSpawner(Partie partie) {
        this.partie = partie;
        this.rand = new Random();
        this.tickCount = 0;
        this.zombies = new LinkedList<>();

        // Ajouter les zombies à la liste
        zombies.add(new NormalZombie(11.0,0));
        zombies.getFirst().setHeating(true);
    }

    // Gestion du spawn des zombies à chaque tick
    public void tick() {
        tickCount++;

        // Définir un intervalle pour le spawn des zombies, par exemple tous les 3 ticks
        if (tickCount % 50 == 0) {
            // Choisir aléatoirement une ligne sur laquelle spawn un zombie
            int rowIndex = rand.nextInt(5);  // On a 5 lignes possibles

            // Créer une ligne de zombies pour cette itération
            partie.getOneRow(rowIndex).addZombie(zombies.getFirst().clone(10.0,rowIndex));  // Ajouter le zombie à la ligne
        }
    }
}
