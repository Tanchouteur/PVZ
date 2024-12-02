package fr.tanchou.pvz.game.board;

import fr.tanchou.pvz.player.Player;
import javafx.scene.layout.Pane;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class SunManager {
    private final LinkedList<Sun> suns; // Liste des soleils actifs
    private final Random random;

    private int timeToLastSpawn = 0;

    public SunManager() {
        this.suns = new LinkedList<>();
        this.random = new Random();
    }

    // Ajouter un soleil à une position donnée
    public void addSun(double x, int y, int value) {
        Sun sun = new Sun(x, y, value,0);
        suns.add(sun);
    }

    // Faire apparaître un soleil à une position aléatoire
    public void spawnRandomSun() {
        double x = random.nextDouble() * 10;
        int y = random.nextInt()%6; // Limite la zone de spawn
        addSun(x, y, 50);
    }

    // Mettre à jour les soleils
    public void updateSuns(Player player) {
        Iterator<Sun> iterator = suns.iterator();
        timeToLastSpawn++;

        while (iterator.hasNext()) {
            Sun sun = iterator.next();
            sun.tick();

            // Supprimer les soleils collectés ou en dehors de l'écran
            if (sun.isCollected()) {
                player.addSun(sun.getValue());
                iterator.remove();
            }
        }

        // Faire apparaître un soleil aléatoire toutes les 50 ticks
        if (timeToLastSpawn > 500) {
            //spawnRandomSun();
            timeToLastSpawn = 0;
        }
    }

    public void updateSunsView(Pane parent) {
        for (Sun sun : suns) {
            if (sun.getVue() == null) {
                SunVue sunVue = (SunVue) sun.createVue(parent);
                sun.setVue(sunVue);
                parent.getChildren().add(sunVue.getImageView());
            }
        }
    }
}
