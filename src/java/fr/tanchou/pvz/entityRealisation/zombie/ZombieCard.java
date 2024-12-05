package fr.tanchou.pvz.entityRealisation.zombie;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public class ZombieCard {
    private final Zombie zombie;
    private int weight;

    public ZombieCard(Zombie zombie, int weight) {
        this.zombie = zombie;
        this.zombie.setHeating(true);
        this.weight = weight;
    }

    public Zombie getZombie() {
        return zombie;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
