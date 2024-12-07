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
    private int spawnTick = 0;

    private final ZombieCard[] zombiesCardArray;

    private enum State { INIT, CRESCENDO, WAVE1, INTERLUDE, WAVE2, FINISHED }
    private State currentState;

    public ZombieSpawner(Partie partie) {
        this.partie = partie;
        this.rand = new Random();
        this.tickCount = 0;
        this.spawnRate = 220;
        this.inWave = false;
        this.zombiesToSpawn = 0;

        this.zombiesCardArray = new ZombieCard[]{
                new ZombieCard(new NormalZombie(11.0,0), 40),
                new ZombieCard(new ConeHeadZombie(11.0,0), 0),
                new ZombieCard(new BucketHeadZombie(11.0,0), 0)
        };

        this.currentState = State.INIT;
    }

    public void tick() {
        tickCount++;
        totalTick++;
        spawnTick++;

        if (totalTick % 50 == 0) {
            spawnRate -= 3;
            System.out.println("Spawn rate : " + spawnRate + " - spawnTick : " + spawnTick);
        }

        if (tickCount % 250 == 0) {
            for (ZombieCard card : zombiesCardArray) {
                if (card.getZombie() instanceof ConeHeadZombie) {
                    card.addWeight(4);
                } else if (card.getZombie() instanceof BucketHeadZombie) {
                    card.addWeight(2);
                }else if (card.getZombie() instanceof NormalZombie) {
                    card.addWeight(1);
                }
            }
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
        if (tickCount > 82) {
            currentState = State.CRESCENDO;
            tickCount = 0;
            spawnTick /= 2;
            System.out.println("Spawn commence");
        }
    }

    private void handleCrescendoPhase() {
        if (spawnTick > (spawnRate + rand.nextInt(25))) {
            System.out.println("Zombie demandé + " + tickCount % (spawnRate));
            spawnZombie();
            spawnTick = 0;
        }

        if (tickCount > 1200) {
            currentState = State.WAVE1;
            zombiesToSpawn = 15 + rand.nextInt(11);
            tickCount = 0;
            rand.setSeed(System.currentTimeMillis());
            System.out.println("first Wave commence");
            inWave = true;
        }
    }

    private void handleWavePhase(int waveNumber) {
        if (zombiesToSpawn > 0 && spawnTick > (15 + rand.nextInt(10))) {
            spawnZombie();
            zombiesToSpawn--;
            spawnTick = 0;
        }

        if (totalTick % 50 == 0) {
            spawnRate -= 2;
        }

        if (zombiesToSpawn == 0 && allZombiesDead() && spawnTick > 100) {
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
        if (spawnTick > (spawnRate + rand.nextInt(15))) {
            spawnZombie();
            spawnTick = 0;
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

        if (partie.getOneRow(rowIndex).getListZombies().size() > 2 ||
                (partie.getOneRow(rowIndex).haveZombie() && partie.getOneRow(rowIndex).getListZombies().getLast().getX() > 7.5)) {

            // Essaye une ligne différente en ajustant avec un décalage modulo
            int offset = rand.nextInt(4) + 1; // Décalage aléatoire entre 1 et 4
            rowIndex = (rowIndex + offset) % 5; // Passe à une autre ligne
            System.out.println("Ligne déjà occupée, déplacement à la ligne " + rowIndex);
        }

        System.out.println("mid sp " + rowIndex);

        ZombieCard zombieCard = getRandomZombieCard();

        System.out.println("random card sp  : " + zombieCard.getZombie().getName());

        zombieCard.removeWeight(1);

        System.out.println("random card sp  weight : " + zombieCard.getWeight());

        partie.getOneRow(rowIndex).addZombie(zombieCard.getZombie().clone(9.0, rowIndex));

        System.out.println("end sp " + rowIndex);

    }

    private ZombieCard getRandomZombieCard() {
        int totalWeight = calculateTotalWeight(); // Somme des poids actuels
        int randomValue = rand.nextInt(totalWeight); // Générer un nombre aléatoire jusqu'à totalWeight

        for (ZombieCard card : zombiesCardArray) {
            System.err.println(card.getZombie().getName() + " weight : " + card.getWeight());
        }

        int currentSum = 0;
        for (ZombieCard card : zombiesCardArray) {
            currentSum += card.getWeight(); // Ajoute le poids dynamique de la carte
            //System.err.println(card.getZombie().getName() + " weight : " + card.getWeight());
            if (randomValue < currentSum) {
                return card;
            }
        }
        throw new IllegalStateException("No zombie card found");
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

    public void manualSpawnZombie(int rowIndex) {

        //ZombieCard zombieCard = getRandomZombieCard();

        ZombieCard zombieCard = new ZombieCard(new ConeHeadZombie(22.0,0), 40);
        Zombie zombie = zombieCard.getZombie().clone(1.0, rowIndex);
        zombie.setHeating(true);
        partie.getOneRow(rowIndex).addZombie(zombie);

    }
}
