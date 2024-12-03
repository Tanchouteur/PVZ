package fr.tanchou.pvz;

import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.PeaShooter;
import fr.tanchou.pvz.entityRealisation.plants.PlantCard;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.SunFlower;
import fr.tanchou.pvz.game.SunManager;

public class Player {
    private int sold;
    private PlantCard[] plantCards;
    private final String name;
    private PlantCard activPlantCard;

    private final SunManager sunManager;
    private int lastCollectSun = 0;

    public Player(String name, SunManager sunManager) {
        this.name = name;
        this.sold = 100;
        this.plantCards = new PlantCard[5];

        this.setPlantCards(new PlantCard[]{
                new PlantCard(240, new PeaShooter(-1, -1)),
                new PlantCard(240, new SunFlower(-1, -2))
        });

        this.sunManager = sunManager;
    }

    public void tick() {
        for (PlantCard plantCard : plantCards) {
            plantCard.tick();
        }
        this.collectSun();
    }

    public void collectSun() {
        lastCollectSun++;
        if (lastCollectSun > 24 && !sunManager.getSunLinkedList().isEmpty()) {
            for (Sun sun : sunManager.getSunLinkedList()){
                sold += sun.getValue();
                sunManager.getSunLinkedList().remove(sun);
            }
            lastCollectSun = 0;
        }
    }

    public int getSold() {
        return sold;
    }

    public void buyPlant(PlantCard plantCard) {
        this.sold -= plantCard.getPlant().getCost();
        this.activPlantCard = plantCard;
    }

    public void cancelBuyPlant() {
        this.sold += activPlantCard.getPlant().getCost();
        this.activPlantCard = null;
    }

    public PlantCard getActivPlantCard() {
        return activPlantCard;
    }

    public void setActivPlantCard(PlantCard activPlantCard) {
        this.activPlantCard = activPlantCard;
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
