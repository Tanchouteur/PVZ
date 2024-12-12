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
        double[] inputs = new double[270];

        // Remplir les 225 entrées pour les plantes
        int index = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                PlantCase plantCase = partie.getOneRow(row).getPlantCase(col);

                if (plantCase.getPlant() == null) {
                    inputs[index++] = 0.0; // Pas de plante
                    inputs[index++] = 0.0; // Pas de type de plante
                } else {
                    inputs[index++] = (double) plantCase.getPlant().getHealthPoint() / plantCase.getPlant().getOriginalHealth();  // Vie de la plante

                    switch (plantCase.getPlant().getName()) {// ou un id représentant le type de plante
                        case "SunFlower":
                            inputs[index++] = 0.1;
                            break;

                        case "PeaShooter":
                            inputs[index++] = 0.2;
                            break;

                        case "FreezePeaShooter":
                            inputs[index++] = 0.3;
                            break;

                        case "DoublePeaShooter":
                            inputs[index++] = 0.4;
                            break;

                        case "PotatoMine":
                            inputs[index++] = 0.5;
                            break;

                        case "WallNut":
                            inputs[index++] = 0.6;
                            break;
                    }
                }

                inputs[index++] = plantCase.getX() / 8.0; // Position x de la plante
                inputs[index++] = plantCase.getY() / 5.0; // Position y de la plante
                inputs[index++] = plantCase.isEmpty() ? 1.0 : 0.0;  // 1 si occupée, 0 sinon

            }
        }

        // Remplir les 15 entrées pour les zombies
        for (int i = 0; i < 5; i++) {
            Zombie firstZombie = partie.getOneRow(i).getFirstZombie();

            if (firstZombie != null) {
                inputs[index++] = (double) firstZombie.getHealthPoint() / firstZombie.getOriginalHealth(); // Vie du zombie
                inputs[index++] = firstZombie.getX() / 10.0; // Position x du zombie
                inputs[index++] = firstZombie.getY() / 4.0; // Position y du zombie

            } else {
                inputs[index++] = 0.0; // Pas de zombie
                inputs[index++] = 10.0; // Pas de position x
                inputs[index++] = i; // Pas de position y
            }
        }

        // Remplir les 5 entrées pour les tondeuses
        for (int i = 0; i < 5; i++) {
            Mower mower = partie.getOneRow(i).getMower();
            inputs[index++] = mower != null ? 1.0 : 0.0; // 1 si activée
        }

        // Remplir l'entrée pour le soleil
        inputs[index++] = partie.getPlayer().getSold() / 1500.0;

        // Remplir les 24 entrées pour les cartes de plantes
        for (int i = 0; i < 6; i++) {
            PlantCard card = partie.getPlayer().getPlantCardsArray()[i];

            inputs[index++] = (double) card.getPlant().getHealthPoint() / 1100; // vie de la plante
            inputs[index++] = card.canBuy() ? 1.0 : 0.0; // 1 si achetable

            if (card.getPlant() instanceof ObjectGeneratorsPlant plant) {

                if (plant.getObjectOfPlant() instanceof Bullet bullet) {
                    inputs[index++] = bullet.getDamage() / 100.0; // Dégâts du projectile
                    inputs[index++] = 0.0; // genere pas du soleil

                } else if (plant.getObjectOfPlant() instanceof Sun) {
                    inputs[index++] = 0.0; // 0 si c'est un soleil
                    inputs[index++] = 1.0; // genere du soleil
                }
            } else {
                inputs[index++] = 0.0; // 1 si génère du soleil
                inputs[index++] = 0.0;  // 1 si achetable
            }
        }

        //System.out.println("Inputs: " + inputs.length);
        return inputs;
    }

    // Utiliser le réseau pour prendre une décision
    public void takeAction(Partie partie) {
        double[] inputs = getInputs(partie);
        neuralNetwork.feedForward(inputs);// utiliser le réseau pour obtenir les sorties

        double[] outputs = neuralNetwork.getOutput();
        //System.out.println("want plant Output : " + outputs[outputs.length - 1]);

        if (outputs[outputs.length - 1] > 0.5) {
            int plantCardIndex = choosePlantCard(outputs);

            //System.out.println("plantCardIndex : " + plantCardIndex);

            int[] position = choosePosition(outputs);  // Retourne un tableau de 2 entiers : [x, y]

            //System.out.println("position : " + position[0] + ", " + position[1]);

            // Appel à la méthode buyPlant avec x et y
            partie.getPlayer().buyPlant(plantCardIndex, position[0], position[1]);
        }
    }

    // Choisir une carte de plante basée sur les sorties du réseau
    private int choosePlantCard(double[] outputs) {
        // Trouver l'index de la sortie la plus élevée parmi les 6 cartes
        return getMaxIndex(outputs, 0, 6);
    }

    // Choisir une position en fonction des sorties
    private int[] choosePosition(double[] outputs) {
        int positionIndex = getMaxIndex(outputs, 6, 51); // Les sorties pour les positions (6 à 51)

        // Calculer les coordonnées x et y en fonction de l'index
        int x = positionIndex % 9; // 9 colonnes
        int y = positionIndex / 9; // 5 lignes

        return new int[] {x, y}; // Retourne les coordonnées sous forme de tableau
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