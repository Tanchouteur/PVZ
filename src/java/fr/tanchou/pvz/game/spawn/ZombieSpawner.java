package fr.tanchou.pvz.game.spawn;

import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.zombie.BucketHeadZombie;
import fr.tanchou.pvz.entityRealisation.zombie.ConeHeadZombie;
import fr.tanchou.pvz.entityRealisation.zombie.NormalZombie;
import fr.tanchou.pvz.entityRealisation.zombie.ZombieCard;
import fr.tanchou.pvz.game.Partie;

import java.util.Random;

public class ZombieSpawner {

    private final Partie partie;
    private final Random rand;
    private int tickCount;       // Compteur global de ticks
    private int spawnRate;       // Intervalle entre les spawns
    private boolean inWave;      // Indique si une vague est en cours
    private int zombiesToSpawn;  // Nombre de zombies à spawn dans une vague
    private int totalTick = 0;

    private final ZombieCard[] zombiesCardArray;

    private enum State { INIT, CRESCENDO, WAVE1, INTERLUDE, WAVE2, FINISHED }
    private State currentState;

    public ZombieSpawner(Partie partie) {
        this.partie = partie;
        this.rand = new Random();
        this.tickCount = 0;
        this.spawnRate = 70;
        this.inWave = false;
        this.zombiesToSpawn = 0;

        this.zombiesCardArray = new ZombieCard[]{
                new ZombieCard(new NormalZombie(11.0,0), 40),
                new ZombieCard(new ConeHeadZombie(11.0,0), 25),
                new ZombieCard(new BucketHeadZombie(11.0,0), 10)
        };

        this.currentState = State.INIT;
    }

    public void tick() {
        tickCount++;
        totalTick++;

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
        System.err.println("Partie terminée : Victoire !");
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

        if (tickCount > 600) {
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

        ZombieCard zombieCard = getRandomZombieCard();

        assert zombieCard != null;
        partie.getOneRow(rowIndex).addZombie(zombieCard.getZombie().clone(9.0, rowIndex));
    }

    public void spawnZombie(int rowIndex) {

        //ZombieCard zombieCard = getRandomZombieCard();

        ZombieCard zombieCard = new ZombieCard(new BucketHeadZombie(22.0,0), 40);
        Zombie zombie = zombieCard.getZombie().clone(10.0, rowIndex);
        zombie.setHeating(true);
        partie.getOneRow(rowIndex).addZombie(zombie);

    }

    private ZombieCard getRandomZombieCard() {

        //int maxWeight = calculateWeights();

        int maxWeight = 75;

        int cumulativeWeight = 0;
        int reach = rand.nextInt(maxWeight);
        for (ZombieCard zombieCard : zombiesCardArray){
            cumulativeWeight += zombieCard.getWeight();
            if (cumulativeWeight > reach){
                return zombieCard;
            }
        }

        System.err.println("\n Probleme avec les poids de zombie \n max " + maxWeight + " reach " + reach + " cumulative " + cumulativeWeight);
        return null;
    }

    public int calculateWeights() {
        int totalWeight = 0; // Somme des poids pour la normalisation
        System.out.println("begin calculate Weight total tick : " + totalTick);
        // Calcul des nouveaux poids
        double[] newWeights = new double[zombiesCardArray.length];
        newWeights[0] =  Math.round(zombiesCardArray[0].getWeight() * (totalTick / 140.0));
        newWeights[1] =  Math.round(zombiesCardArray[1].getWeight() * (totalTick / 120.0));
        newWeights[2] =  Math.round(zombiesCardArray[2].getWeight() * (totalTick / 100.0));
        System.out.println("1 calculate Weight total tick : " + totalTick);
        // Calcul du poids total
        for (double weight : newWeights) {
            totalWeight += (int) weight;
        }
        System.out.println("2 calculate Weight total tick : " + totalTick);
        // Normalisation pour éviter la perte de précision
        for (int i = 0; i < zombiesCardArray.length; i++) {
            zombiesCardArray[i].setWeight((int) newWeights[i]);
        }
        System.out.println("end calculate Weight total tick : " + totalTick);
        return totalWeight; // Facultatif, si tu n'en as pas besoin, tu peux supprimer ce retour
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
}
