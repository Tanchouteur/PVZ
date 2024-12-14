package fr.tanchou.pvz.ia.network;

import fr.tanchou.pvz.abstractEnity.abstracObjectOfPlant.Bullet;
import fr.tanchou.pvz.abstractEnity.abstractPlant.ObjectGeneratorsPlant;
import fr.tanchou.pvz.abstractEnity.abstractZombie.Zombie;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.PlantCard;
import fr.tanchou.pvz.game.rowComponent.Mower;
import fr.tanchou.pvz.game.rowComponent.PlantCase;

public class GameAI {
    private final NeuralNetwork neuralNetwork;

    public GameAI(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    // Convertir l'état du jeu en un tableau d'entrées
    private double[] getInputs(Partie partie) {
        double[] inputs = new double[230];
        int index = 0;

        // Remplir les 45 entrées pour les plantes (5 lignes x 9 colonnes)
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                PlantCase plantCase = partie.getOneRow(row).getPlantCase(col);

                // Si pas de plante
                if (plantCase.getPlant() == null) {
                    inputs[index++] = 0.0; // Pas de plante
                    inputs[index++] = 0.0; // Pas de type de plante
                } else {
                    // Normaliser la vie de la plante
                    inputs[index++] = (double) plantCase.getPlant().getHealthPoint() / (plantCase.getPlant().getOriginalHealth() + 1);

                    // Encodage ordinal ou binaire simplifié pour le type de plante
                    switch (plantCase.getPlant().getName()) {
                        case "SunFlower":
                            inputs[index++] = 0.1; break;
                        case "PeaShooter":
                            inputs[index++] = 0.2; break;
                        case "FreezePeaShooter":
                            inputs[index++] = 0.3; break;
                        case "DoublePeaShooter":
                            inputs[index++] = 0.4; break;
                        case "PotatoMine":
                            inputs[index++] = 0.5; break;
                        case "WallNut":
                            inputs[index++] = 0.6; break;
                        default:
                            inputs[index++] = 0.0; break;
                    }

                }
                // Positions normalisées (1-0)
                inputs[index++] = plantCase.getX() / 8.0; // Position x
                inputs[index++] = plantCase.getY() / 4.0; // Position y
            }
        }

        // Remplir les 15 entrées pour les zombies (5 lignes x 3 caractéristiques)
        for (int i = 0; i < 5; i++) {
            Zombie firstZombie = partie.getOneRow(i).getFirstZombie();

            // Si un zombie est présent
            if (firstZombie != null) {
                inputs[index++] = (double) firstZombie.getHealthPoint() / firstZombie.getOriginalHealth(); // Vie du zombie
                inputs[index++] = firstZombie.getX() / 10.0; // Position x
                inputs[index++] = firstZombie.getY() / 4.0; // Position y
            } else {
                // Si pas de zombie
                inputs[index++] = 0.0;  // Vie du zombie
                inputs[index++] = 1.0;  // Position x (non active)
                inputs[index++] = (double) i / 4.0; // Position y
            }
        }

        // Remplir les entrées de dangerosité pour chaque ligne
        for (int i = 0; i < 5; i++) {
            double dangerLevel = partie.getOneRow(i).calculateDangerLevel();
            inputs[index++] = dangerLevel; // Ajouter le score de dangerosité pour la ligne i
        }

        // Remplir les 5 entrées pour les tondeuses
        for (int i = 0; i < 5; i++) {
            Mower mower = partie.getOneRow(i).getMower();
            inputs[index++] = mower != null ? 1.0 : 0.0; // 1 si activée, 0 sinon
        }

        // Remplir l'entrée pour le soleil
        inputs[index++] = partie.getPlayer().getSold() / 1500.0; // Normaliser le nombre de soleils

        // Remplir les 24 entrées pour les cartes de plantes (6 cartes x 4 caractéristiques par carte)
        for (int i = 0; i < 6; i++) {
            PlantCard card = partie.getPlayer().getPlantCardsArray()[i];

            // Normalisation de la vie de la plante
            inputs[index++] = (double) card.getPlant().getHealthPoint() / 1100;

            // 1 si achetable, 0 sinon
            inputs[index++] = card.canBuy() ? 1.0 : 0.0;

            // Si la plante génère du soleil (si c'est un générateur de soleil)
            if (card.getPlant() instanceof ObjectGeneratorsPlant plant) {
                if (plant.getObjectOfPlant() instanceof Sun) {
                    inputs[index++] = 1.0; // Génère du soleil
                } else {
                    inputs[index++] = 0.0; // N'engendre pas de soleil
                }
            } else {
                inputs[index++] = 0.0; // N'engendre pas de soleil
            }

            // Indication des dégâts de la plante (si projectile)
            if (card.getPlant() instanceof ObjectGeneratorsPlant plant) {
                if (plant.getObjectOfPlant() instanceof Bullet bullet) {
                    inputs[index++] = bullet.getDamage() / 100.0; // Normaliser les dégâts du projectile
                } else {
                    inputs[index++] = 0.0;
                }
            }
        }

        return inputs;
    }


    // Utiliser le réseau pour prendre une décision
    public void takeAction(Partie partie) {

        double[] inputs = getInputs(partie);

        try {
            neuralNetwork.feedForward(inputs);// utiliser le réseau pour obtenir les sorties
        }catch (Exception e) {
            System.err.println("Erreur lors du feedForward de l'IA: " + e.getMessage());
        }

        //System.out.println("getOutput");
        double[] outputs = neuralNetwork.getOutput();

        //System.out.println("takeAction 3 ");

        //System.out.println("want plant Output : " + outputs[outputs.length - 1]);

        if (outputs[outputs.length - 1] > 0.5) {
            int plantCardIndex = choosePlantCard(outputs);

            //System.out.println("4 plantCardIndex : " + plantCardIndex);

            int[] position = choosePosition(outputs);  // Retourne un tableau de 2 entiers : [x, y]

            //System.out.println("5 position : " + position[0] + ", " + position[1]);

            // Appel à la méthode buyPlant avec x et y.
            partie.getPlayer().buyPlant(plantCardIndex, position[0], position[1]);

            //System.out.println("6 buyPlant : " + plantCardIndex + ", " + position[0] + ", " + position[1]);
        }
        //System.out.println("end takeAction");
    }

    // Choisir une carte de plante basée sur les sorties du réseau
    private int choosePlantCard(double[] outputs) {
        // Trouver l'index de la sortie la plus élevée parmi les 6 cartes
        return getMaxIndex(outputs, 0, 6);
    }

    // Choisir une position en fonction des sorties
    private int[] choosePosition(double[] outputs) {
        // Vérifie que la plage est correcte
        if (outputs.length < 52) {
            throw new IllegalArgumentException("La taille du tableau des sorties doit être au moins de 52.");
        }

        // Trouve l'index correspondant à la valeur maximale dans la plage 6 à 51
        int positionIndex = getMaxIndex(outputs, 6, 51);

        // Vérifie que positionIndex est dans les limites prévues
        if (positionIndex < 6 || positionIndex > 51) {
            throw new IllegalStateException("L'index calculé pour la position est en dehors de la plage attendue.");
        }

        // Ajuste l'index pour le ramener entre 0 et 45 (6 à 51 correspond à 46 valeurs possibles)
        positionIndex -= 6;

        // Calculer les coordonnées x et y.
        int x = positionIndex % 9; // Reste de la division par 9 (0 à 8 inclus)
        int y = positionIndex / 9; // Partie entière de la division par 9 (0 à 4 inclus)

        // Retourne les coordonnées sous forme de tableau [x, y]
        return new int[] {x, y};
    }

    // Trouver l'index de la sortie la plus élevée
    private int getMaxIndex(double[] outputs, int start, int end) {

        double max = Double.NEGATIVE_INFINITY;
        int maxIndex = start;

        for (int i = start; i < end; i++) {
            if (outputs[i] > max) {
                max = outputs[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }
}