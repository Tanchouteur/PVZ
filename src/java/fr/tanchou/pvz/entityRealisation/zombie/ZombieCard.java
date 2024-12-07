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

    private void setWeight(int weight) {
        this.weight = weight;
    }

    public void addWeight(int weight) {
        this.weight += weight;
    }

    public void removeWeight(int weight) {
        if (this.weight - weight > 0) {
            this.weight -= weight;
        }
    }
}
