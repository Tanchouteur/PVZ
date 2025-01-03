package fr.tanchou.pvz.game;

import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import java.util.LinkedList;
import java.util.Random;

public class SunManager {
    private final LinkedList<Sun> sunToAdd; // Liste des soleils a ajouter
    private final LinkedList<Sun> sunToRemove; // Liste des soleils a supprimer
    private final LinkedList<Sun> sunLinkedList; // Liste des soleils actifs
    private final Random random;

    private int timeToLastSpawn = 0;

    public SunManager() {
        this.sunLinkedList = new LinkedList<>();
        this.sunToAdd = new LinkedList<>();
        this.sunToRemove = new LinkedList<>();
        this.random = new Random();
    }

    public void tick() {
        // Ajouter les soleils à ajouter
        if (!sunToAdd.isEmpty()) {
            sunLinkedList.addAll(sunToAdd);
            sunToAdd.clear();
        }
        if (!sunToRemove.isEmpty()) {
            sunLinkedList.removeAll(sunToRemove);
            sunToRemove.clear();
        }

        spawnRandomSun();
    }

    // Faire apparaître un soleil à une position aléatoire
    private void spawnRandomSun() {
        timeToLastSpawn++;

        if (timeToLastSpawn >= (100+random.nextInt(10))) {

            timeToLastSpawn = 0;
            double x = random.nextDouble(7); // Limite la zone de spawn
            int y = random.nextInt(4); // Limite la zone de spawn
            addSun(new Sun(x, y, 25));
            //System.out.println("Sun spawned at " + x + " " + y);
        }
    }

    // Ajouter un soleil à une position donnée
    public void addSun(Sun sun) {
        this.sunToAdd.add(sun);
    }

    public void removeSun(Sun sun) {
        this.sunToRemove.add(sun);
    }

    public LinkedList<Sun> getSunLinkedList() {
        return this.sunLinkedList;
    }
}
