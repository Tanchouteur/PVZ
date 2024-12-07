package fr.tanchou.pvz.game.spawn;

import fr.tanchou.pvz.entityRealisation.zombie.BucketHeadZombie;
import fr.tanchou.pvz.entityRealisation.zombie.ConeHeadZombie;
import fr.tanchou.pvz.entityRealisation.zombie.NormalZombie;
import fr.tanchou.pvz.entityRealisation.zombie.ZombieCard;
import fr.tanchou.pvz.game.Partie;

import java.util.Random;

public class ZombieSelector {
    private final Partie partie;
    private final ZombieCard[] zombiesCardArray;
    private Random rand;
    private int[] rowScores = new int[5]; // Score de difficulté pour chaque ligne

    public ZombieSelector(Partie partie) {
        this.partie = partie;
        this.zombiesCardArray = new ZombieCard[]{
                new ZombieCard(new NormalZombie(11.0,0), 40, 1),
                new ZombieCard(new ConeHeadZombie(11.0,0), 0, 2),
                new ZombieCard(new BucketHeadZombie(11.0,0), 0, 3)
        };
    }

    void spawnZombie() {
        rand = new Random();
        int rowIndex = rand.nextInt(5);

        // Vérifie si la ligne est trop chargée en score
        for (int i = 0; i < 5; i++) {
            if (rowScores[rowIndex] > 5) { // Seuil à ajuster selon la difficulté voulue
                // Trouve une autre ligne avec un score plus faible
                int offset = rand.nextInt(4) + 1;
                rowIndex = (rowIndex + offset) % 5;
            } else {
                break;
            }
        }

        System.out.println("Ligne choisie pour spawn : " + rowIndex);

        // Vérifie également les conditions de présence des zombies
        if (partie.getOneRow(rowIndex).getListZombies().size() > 2 ||
                (partie.getOneRow(rowIndex).haveZombie() && partie.getOneRow(rowIndex).getListZombies().getLast().getX() > 7.5)) {

            // Essaye une ligne différente avec un décalage modulo
            int offset = rand.nextInt(4) + 1;
            rowIndex = (rowIndex + offset) % 5;
            System.out.println("Ligne déjà occupée, déplacement à la ligne " + rowIndex);
        }

        // Récupère une carte zombie aléatoire
        ZombieCard zombieCard = getRandomZombieCard();
        zombieCard.removeWeight(1);

        // Clone et ajoute le zombie sur la ligne
        partie.getOneRow(rowIndex).addZombie(zombieCard.getZombie().clone(9.0, rowIndex));

        // Met à jour le score de la ligne avec le score de difficulté du zombie
        rowScores[rowIndex] += zombieCard.getDifficultyScore();

        System.out.println("Fin du spawn. Score de la ligne " + rowIndex + ": " + rowScores[rowIndex]);
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
            System.err.println(card.getZombie().getName() + " weight : " + card.getWeight());
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
        if (partie.getZombieSpawner().isInWave()) {
            if (card.getZombie() instanceof ConeHeadZombie || card.getZombie() instanceof BucketHeadZombie) {
                baseWeight *= 2;
            }
        }

        return baseWeight;
    }

    public void tick() {
        if (partie.getZombieSpawner().getTickCount() % 250 == 0 && partie.getZombieSpawner().getTotalTick() > 500) {
            for (ZombieCard card : zombiesCardArray) {
                if (card.getZombie() instanceof ConeHeadZombie) {
                    card.addWeight(2);
                } else if (card.getZombie() instanceof BucketHeadZombie) {
                    card.addWeight(1);
                } else if (card.getZombie() instanceof NormalZombie) {
                    card.addWeight(1);
                }
            }
        }

        // Réduction des scores des lignes toutes les X ticks
        if (partie.getZombieSpawner().getTickCount() % 500 == 0) {
            for (int i = 0; i < rowScores.length; i++) {
                if (rowScores[i] > 0) {
                    rowScores[i]--; // Diminue le score progressivement
                }
            }
        }
    }
}
