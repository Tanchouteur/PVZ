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
        this.spawnRate = 80;
        this.inWave = false;
        this.zombiesToSpawn = 0;

        this.zombiesCardArray = new ZombieCard[]{
                new ZombieCard(new NormalZombie(11.0,0), 40),
                new ZombieCard(new ConeHeadZombie(11.0,0), 15),
                new ZombieCard(new BucketHeadZombie(11.0,0), 5)
        };

        this.currentState = State.INIT;
    }

    public void tick() {
        tickCount++;
        totalTick++;

        if (totalTick % 50 == 0) {
            spawnRate -= 2;
        }

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
        if (tickCount % (spawnRate + rand.nextInt(10)) == 0) {
            System.out.println("Zombie demandé ");
            spawnZombie();
        }

        if (tickCount > 600) {
            currentState = State.WAVE1;
            zombiesToSpawn = 15 + rand.nextInt(11);
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

            } else if (waveNumber == 2 && allZombiesDead()) {
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

        System.out.println("row " + rowIndex);

        if (partie.getOneRow(rowIndex).getListZombies().size() > 2 || (partie.getOneRow(rowIndex).haveZombie() && partie.getOneRow(rowIndex).getListZombies().getLast().getX() > 7.5)) {
            if (rand.nextBoolean()){
                rowIndex += rand.nextInt(2);
            }else {
                rowIndex -= rand.nextInt(2);
            }

            rowIndex = rowIndex%4;

            System.out.println("Zombie déplacé à la ligne " + rowIndex);
        }

        ZombieCard zombieCard = getRandomZombieCard();

        assert zombieCard != null;
        partie.getOneRow(rowIndex).addZombie(zombieCard.getZombie().clone(9.0, rowIndex));

        rand.setSeed(System.currentTimeMillis());
    }

    public void manualSpawnZombie(int rowIndex) {

        //ZombieCard zombieCard = getRandomZombieCard();

        ZombieCard zombieCard = new ZombieCard(new ConeHeadZombie(22.0,0), 40);
        Zombie zombie = zombieCard.getZombie().clone(1.0, rowIndex);
        zombie.setHeating(true);
        partie.getOneRow(rowIndex).addZombie(zombie);

    }

    private ZombieCard getRandomZombieCard() {
        int totalWeight = calculateTotalWeight(); // Somme des poids actuels
        int randomValue = rand.nextInt(totalWeight); // Générer un nombre aléatoire jusqu'à totalWeight

        int currentSum = 0;
        for (ZombieCard card : zombiesCardArray) {
            currentSum += getDynamicWeight(card); // Ajoute le poids dynamique de la carte
            if (randomValue < currentSum) {
                return card; // Retourne la carte si randomValue tombe dans son intervalle
            }
        }
        return null; // Sécurité (ne devrait pas arriver si les poids sont correctement définis)
    }

    private int calculateTotalWeight() {
        int total = 0;
        for (ZombieCard card : zombiesCardArray) {
            total += getDynamicWeight(card);
        }
        return total;
    }

    private int getDynamicWeight(ZombieCard card) {
        int baseWeight = card.getWeight();

        if (tickCount > 500) {
            if (card.getZombie() instanceof ConeHeadZombie) {
                baseWeight += 9; // ConeHead plus fréquent
            } else if (card.getZombie() instanceof BucketHeadZombie) {
                baseWeight += 10; // BucketHead plus fréquent
            }
        }

        // Si en vague, doublez les poids des zombies solides
        if (isInWave()) {
            if (card.getZombie() instanceof ConeHeadZombie || card.getZombie() instanceof BucketHeadZombie) {
                baseWeight *= 2;
            }
        }

        return baseWeight;
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

    public String getTickCount() {
        return String.valueOf(tickCount);
    }

    public String getZombiesToSpawn() {
        return String.valueOf(zombiesToSpawn);
    }

    public String getCurrentState() {
        return currentState.toString();
    }

    public String getTotalTick() {
        return String.valueOf(totalTick);
    }
}
