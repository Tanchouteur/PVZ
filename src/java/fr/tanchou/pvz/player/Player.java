package fr.tanchou.pvz.player;

import fr.tanchou.pvz.entities.plants.PlantCard;
import fr.tanchou.pvz.entities.plants.passive.sunflower.SunFlower;
import fr.tanchou.pvz.entities.plants.shooter.pea.PeaShooter;

public class Player {
    private int sun;
    private PlantCard[] plantCards;
    private final String name;

    public Player(String name) {
        this.name = name;
        this.sun = 50;
        this.plantCards = new PlantCard[5];

        this.setPlantCards(new PlantCard[]{
                new PeaShooter().getCard(),
                new SunFlower().getCard(),
        });
    }

    public int getSun() {
        return sun;
    }

    public void addSun(int sun) {
        this.sun += sun;
    }

    public void removeSun(int sun) {
        this.sun -= sun;
    }

    public PlantCard[] getPlantCards() {
        return plantCards;
    }

    public void setPlantCards(PlantCard[] plantCards) {
        this.plantCards = plantCards;
    }

    public void setPlantCard(int index, PlantCard plantCard) {
        this.plantCards[index] = plantCard;
    }

    public String getName() {
        return name;
    }
}
