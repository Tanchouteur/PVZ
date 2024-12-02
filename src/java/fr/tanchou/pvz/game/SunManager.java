package fr.tanchou.pvz.game;

import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import java.util.LinkedList;
import java.util.Random;

public class SunManager {
    private final LinkedList<Sun> sunLinkedList; // Liste des soleils actifs
    private final Random random;

    private int timeToLastSpawn = 0;

    public SunManager() {
        this.sunLinkedList = new LinkedList<>();
        this.random = new Random();
    }

    // Ajouter un soleil à une position donnée
    public void addSun(Sun sun) {
        sunLinkedList.add(sun);
    }

    public void tick() {
        timeToLastSpawn++;
        spawnRandomSun();
    }

    // Faire apparaître un soleil à une position aléatoire
    private void spawnRandomSun() {
        if (timeToLastSpawn > 100) {
            timeToLastSpawn = 0;
            double x = random.nextDouble() * 10;
            int y = random.nextInt()%6; // Limite la zone de spawn
            addSun(new Sun(x, y, 25));
        }
    }

    public LinkedList<Sun> getSunLinkedList() {
        return sunLinkedList;
    }
}