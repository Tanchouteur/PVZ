package fr.tanchou.pvz.player;

import fr.tanchou.pvz.entities.plants.PlantCards;

import java.util.LinkedList;

public class PlayerPlants {
    private final LinkedList<PlantCards> plantCards;

    public PlayerPlants(LinkedList<PlantCards> plantCards) {
        this.plantCards = new LinkedList<>();
    }

    public LinkedList<PlantCards> getPlantCards() {
        return plantCards;
    }
}
