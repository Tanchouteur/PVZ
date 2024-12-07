package fr.tanchou.pvz.entityRealisation.zombie;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;

public class ZombieCard {
    private final Zombie zombie;
    private int weight;
    private final int difficultyScore;

    public ZombieCard(Zombie zombie, int weight, int difficultyScore) {
        this.zombie = zombie;
        this.zombie.setHeating(true);
        this.weight = weight;
        this.difficultyScore = difficultyScore;
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
        if (this.weight - weight > 1) {
            this.weight -= weight;
        }
    }

    public int getDifficultyScore() {
        return difficultyScore;
    }
}
