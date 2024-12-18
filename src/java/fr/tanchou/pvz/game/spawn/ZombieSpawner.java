package fr.tanchou.pvz.game.spawn;

import fr.tanchou.pvz.game.Partie;

import java.util.Random;

public class ZombieSpawner {

    private final Partie partie;
    private final ZombieSelector zombieSelector;

    private final Random rand = new Random();

    private int spawnRate = 220;       // Intervalle entre les spawns

    private int tickCount = 0;
    private int totalTick = 0;
    private int spawnTick = 0;

    private int zombiesToSpawn = 0;  // Nombre de zombies à spawn dans une vague
    private boolean inWave = false;      // Indique si une vague est en cours

    private enum State { INIT, CRESCENDO, WAVE1, INTERLUDE, WAVE2, FINISHED }
    private State currentState = State.INIT;

    private int randomSeed = 0;

    public ZombieSpawner(Partie partie, int random) {
        this.partie = partie;
        this.zombieSelector = new ZombieSelector(partie, rand);

        this.randomSeed = random;
        if (random == 0) {
            rand.setSeed(System.currentTimeMillis());
        }else {
            rand.setSeed(random);
        }
    }

    private void decrementSpawnRate(int valueToDecrement) {
        if (spawnRate - valueToDecrement < 0) {
            spawnRate = 0;
        }
    }

    public void tick() {
        tickCount++;
        totalTick++;
        spawnTick++;

        if (totalTick % 50 == 0) {
            decrementSpawnRate(4);
            //System.out.println("Spawn rate : " + spawnRate + " - spawnTick : " + spawnTick);
        }

        zombieSelector.tick();

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
        //System.err.println("Partie terminée : Victoire !");
        partie.setVictory(true);
    }

    private void handleInitPhase() {
        if (tickCount > 82) {
            currentState = State.CRESCENDO;
            tickCount = 0;
            spawnTick /= 2;
            //System.out.println("Spawn commence");
        }
    }

    private void handleCrescendoPhase() {
        if (spawnTick > (spawnRate + rand.nextInt(25))) {
            //System.out.println("Zombie demandé + " + tickCount % (spawnRate));
            zombieSelector.spawnZombie();
            spawnTick = 0;
        }

        if (tickCount > 1300) {
            currentState = State.WAVE1;
            zombiesToSpawn = 15 + rand.nextInt(5);
            tickCount = 0;
            if (randomSeed == 0) {
                rand.setSeed(System.currentTimeMillis());
            }else {
                rand.setSeed(randomSeed);
            }
            //System.out.println("first Wave commence");
            inWave = true;
        }
    }

    private void handleWavePhase(int waveNumber) {
        if (waveNumber == 1) {
            if (zombiesToSpawn > 0 && spawnTick > (21 + rand.nextInt(15))) {
                zombieSelector.spawnZombie();
                zombiesToSpawn--;
                spawnTick = 0;
            }
        }else if (waveNumber == 2) {
            if (zombiesToSpawn > 0 && spawnTick > (10 + rand.nextInt(15))) {
                zombieSelector.spawnZombie();
                zombiesToSpawn--;
                spawnTick = 0;
            }
            if (totalTick % 50 == 0) {
                decrementSpawnRate(2);
            }
        }


        if (totalTick % 50 == 0) {
            decrementSpawnRate(1);
        }

        if (zombiesToSpawn == 0 && allZombiesDead() && spawnTick > 100) {
            if (waveNumber == 1) {

                currentState = State.INTERLUDE;
                tickCount = 0;
                inWave = false;
                //System.out.println("interlude apres la premiere vague");

            } else if (waveNumber == 2 && allZombiesDead()) {
                currentState = State.FINISHED;
            }
        }
    }

    private void handleInterludePhase() {
        int tempSpawnRate = spawnRate;
        if (tempSpawnRate > 45) {
            tempSpawnRate /= 2;
        }
        if (spawnTick > (tempSpawnRate + rand.nextInt(20))) {
            zombieSelector.spawnZombie();
            spawnTick = 0;
        }

        if (tickCount > 600) {
            currentState = State.WAVE2;
            zombiesToSpawn = 35;
            tickCount = 0;
            inWave = true;
            //System.out.println("Deuxième vague commence !");
        }
    }

    private boolean allZombiesDead() {
        for (int i = 0; i < 5; i++) {
            if (partie.getOneRow(i).haveZombie()) {
                return false;
            }
        }
        return true;
    }

    public boolean isInWave() {
        return inWave;
    }

    public int getTickCount() {
        return tickCount;
    }

    public String getZombiesToSpawn() {
        return String.valueOf(zombiesToSpawn);
    }

    public String getCurrentState() {
        return currentState.toString();
    }

    public int getTotalTick() {
        return totalTick;
    }
}
