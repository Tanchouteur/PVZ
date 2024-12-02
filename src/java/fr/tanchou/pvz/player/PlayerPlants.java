package fr.tanchou.pvz.player;

import fr.tanchou.pvz.entityRealisation.plants.PlantCard;

import java.util.LinkedList;

public class PlayerPlants {
    private final LinkedList<PlantCard> plantCards;

    public PlayerPlants(LinkedList<PlantCard> plantCards) {
        this.plantCards = new LinkedList<>();
    }

    public LinkedList<PlantCard> getPlantCards() {
        return plantCards;
    }
}
