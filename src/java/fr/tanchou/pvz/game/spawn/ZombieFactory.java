package fr.tanchou.pvz.game.spawn;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.zombie.ConeheadZombie;
import fr.tanchou.pvz.entityRealisation.zombie.NormalZombie;

public class ZombieFactory {

    // Méthode pour créer un zombie avec des caractéristiques basiques
    public Zombie createZombie(ZombieType type, int rowNumber) {
        Zombie zombie;
        switch(type) {
            case NORMAL -> zombie = new NormalZombie(10.0, rowNumber);
            case CONEHEAD -> zombie = new ConeheadZombie(10.0, rowNumber);
            case BUCKETHEAD -> zombie = new ConeheadZombie(10.0, rowNumber);

            default -> throw new IllegalArgumentException("Zombie type not supported: " + type);
        }
        return zombie;
    }
}
