package fr.tanchou.pvz.player;

import fr.tanchou.pvz.entities.PlantCards;

public class Player {
    private int sun;
    private PlantCards plantCards;
    private final String name;

    public Player(String name) {
        this.name = name;
        this.sun = 50;
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

    public PlantCards getPlantCards() {
        return plantCards;
    }

    public void setPlantCards(PlantCards plantCards) {
        this.plantCards = plantCards;
    }

    public String getName() {
        return name;
    }
}
