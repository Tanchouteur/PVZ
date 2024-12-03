package fr.tanchou.pvz.game.spawn;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.zombie.ConeheadZombie;
import fr.tanchou.pvz.entityRealisation.zombie.NormalZombie;
import fr.tanchou.pvz.game.Partie;

import java.util.Random;

public class ZombieSpawner {

    private final Partie partie;
    private final Random rand;
    private int tickCount;       // Compteur global de ticks
    private int spawnRate;       // Intervalle entre les spawns
    private boolean inWave;      // Indique si une vague est en cours
    private int zombiesToSpawn;  // Nombre de zombies à spawn dans une vague

    private final Zombie[] zombiesArray;

    private enum State { INIT, CRESCENDO, WAVE1, INTERLUDE, WAVE2, FINISHED }
    private State currentState;

    public ZombieSpawner(Partie partie) {
        this.partie = partie;
        this.rand = new Random();
        this.tickCount = 0;
        this.spawnRate = 70;
        this.inWave = false;
        this.zombiesToSpawn = 0;


        this.zombiesArray = new Zombie[6];

        // Ajouter les zombies à la liste
        zombiesArray[0] = new NormalZombie(11.0,0);
        zombiesArray[1] = new ConeheadZombie(11.0, 0);
        zombiesArray[0].setHeating(true);
        zombiesArray[1].setHeating(true);

        this.currentState = State.INIT;
    }

    public void tick() {
        tickCount++;

        switch (currentState) {
            case INIT -> handleInitPhase();
            case CRESCENDO -> handleCrescendoPhase();
            case WAVE1 -> handleWavePhase(1);
            case INTERLUDE -> handleInterludePhase();
            case WAVE2 -> handleWavePhase(2);
            case FINISHED -> handleEndGame();
        }

    }

    private void handleEndGame() {
        System.out.println("Partie terminée : Victoire !");
        partie.setVictory(true);
    }

    private void handleInitPhase() {
        if (tickCount > 75) {  //5 sec
            currentState = State.CRESCENDO;
            tickCount = 0;
            System.out.println("Spawn commence");
        }
    }

    private void handleCrescendoPhase() {
        if (tickCount % spawnRate == 0) {
            spawnZombie();
            spawnRate = Math.max(20, spawnRate - 1);  // Augmenter progressivement la vitesse de spawn
        }

        if (tickCount > 800) {
            currentState = State.WAVE1;
            zombiesToSpawn = 20;
            tickCount = 0;
            System.out.println("first Wave commence");
            inWave = true;
        }
    }

    private void handleWavePhase(int waveNumber) {
        if (zombiesToSpawn > 0 && tickCount % 10 == 0) {
            spawnZombie();
            zombiesToSpawn--;
        }

        if (zombiesToSpawn == 0 && allZombiesDead()) {
            if (waveNumber == 1) {

                currentState = State.INTERLUDE;
                tickCount = 0;
                inWave = false;
                System.out.println("interlude apres la premiere vague");

            } else if (waveNumber == 2) {
                currentState = State.FINISHED;
            }
        }
    }

    private void handleInterludePhase() {
        if (tickCount % spawnRate == 0) {
            spawnZombie();
        }

        if (tickCount > 600) {
            currentState = State.WAVE2;
            zombiesToSpawn = 30;
            tickCount = 0;
            inWave = true;
            System.out.println("Deuxième vague commence !");
        }
    }

    private void spawnZombie() {
        int rowIndex = rand.nextInt(5);

        Zombie zombie = zombiesArray[0].clone(10.0, rowIndex);

        partie.getOneRow(rowIndex).addZombie(zombie);

        System.out.println(zombie + " Spawned in row: " + rowIndex);
    }

    public void spawnZombie(int rowIndex) {

        Zombie zombie = zombiesArray[0].clone(7.0, rowIndex);

        partie.getOneRow(rowIndex).addZombie(zombie);

        System.out.println(zombie + " Spawned in row: " + rowIndex);
    }

    private Zombie getRandomZombie() {
        int randomValue = rand.nextInt(100);
        if (randomValue < 60) {
            return zombiesArray[0];
        } else {
            return zombiesArray[1];
        }
    }

    private boolean allZombiesDead() {
        for (int i = 0; i < 5; i++) {
            if (!partie.getOneRow(i).haveZombie()) {
                return false;
            }
        }
        return true;
    }

    public boolean isInWave() {
        return inWave;
    }
}
