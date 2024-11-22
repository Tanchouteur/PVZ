package fr.tanchou.pvz.game.spawn;

import fr.tanchou.pvz.entities.zombie.Zombie;
import fr.tanchou.pvz.entities.zombie.ZombieType;

import java.util.ArrayList;
import java.util.List;

public class SerieRowFactory {

    private final ZombieFactory zombieFactory;

    public SerieRowFactory(ZombieFactory zombieFactory) {
        this.zombieFactory = zombieFactory;
    }

    // Crée une ligne de zombies sur une rangée spécifique
    public List<Zombie> createZombieRow(int rowIndex, int numberOfZombies) {
        List<Zombie> zombies = new ArrayList<>();
        for (int i = 0; i < numberOfZombies; i++) {
            // Randomise le type de zombie
            ZombieType zombieType = (i % 2 == 0) ? ZombieType.NORMAL : ZombieType.CONEHEAD;  // Par exemple, alterner entre lent et rapide
            Zombie zombie = zombieFactory.createZombie(zombieType, rowIndex);
            zombies.add(zombie);
        }
        return zombies;
    }
}

