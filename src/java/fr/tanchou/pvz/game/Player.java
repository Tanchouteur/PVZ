package fr.tanchou.pvz.game;

import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.DoublePeaShooter;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.FreezePeaShooter;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.PeaShooter;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.SunFlower;
import fr.tanchou.pvz.entityRealisation.plants.PotatoMine;
import fr.tanchou.pvz.entityRealisation.plants.passive.WallNut;

public class Player {
    private int sold;
    private PlantCard[] plantCards;
    private final String name;
    private PlantCard activPlantCard;

    private boolean isShovelActive = false;

    private  SunManager sunManager;
    private  Partie partie;

    private int lastCollectSun = 0;

    private int plantPlacedCount = 0;

    public Player(String name) {
        this.name = name;
        this.sold = 50;
        this.plantCards = new PlantCard[5];

        this.setPlantCards(new PlantCard[]{
                new PlantCard(55, new SunFlower(-1, -2), this.sold),
                new PlantCard(200, new PotatoMine(-1, -2), this.sold),
                new PlantCard(70, new PeaShooter(-1, -1), this.sold),
                new PlantCard(200, new FreezePeaShooter(-1, -2), this.sold),
                new PlantCard(320, new DoublePeaShooter(-1, -2), this.sold),
                new PlantCard(330, new WallNut(-1, -2), this.sold)
        });
    }

    public void tick() {
        for (PlantCard plantCard : plantCards) {
            plantCard.tick(this.sold);
        }
        this.collectSun();
    }

    public void collectSun() {
        lastCollectSun++;
        if (lastCollectSun > 24 && !sunManager.getSunLinkedList().isEmpty()) {
            for (Sun sun : sunManager.getSunLinkedList()){
                sold += sun.getValue();
                sunManager.removeSun(sun);
            }
            lastCollectSun = 0;
            //System.err.println("sold : " + sold);
        }
    }

    public int getSold() {
        return sold;
    }

    public void preBuyPlant(PlantCard plantCard) {
        if (this.activPlantCard != null && this.activPlantCard.equals(plantCard)) {

            cancelBuyPlant();

        }else if (plantCard.canBuy() && (this.sold - plantCard.getPlant().getCost()) >= 0) {
            this.isShovelActive = false;
            this.activPlantCard = plantCard;
        }
    }

    public boolean buyPlant(int x, int y) {
        if (activPlantCard != null && this.partie.getOneRow(y).getPlantCase(x).isEmpty()) {
            if (activPlantCard.canBuy()) {

                Plant plant = this.activPlantCard.getNewPlant(x, y);
                this.partie.getOneRow(y).placePlantInCase(plant);
                this.plantPlacedCount++;
                //System.out.println("Plant placed at x: " + x + " y: " + y);

                this.activPlantCard = null;
                this.sold -= plant.getCost();
                return true;
            }

        }
        return false;
    }

    public boolean buyPlant(int plantCardIndex,int x, int y) {
        PlantCard plantCard = plantCards[plantCardIndex];
        if (plantCard.canBuy() && (this.sold - plantCard.getPlant().getCost()) >= 0) {
            this.isShovelActive = false;
            this.activPlantCard = plantCard;
        }

        return buyPlant(x, y);
    }

    public void cancelBuyPlant() {
        this.activPlantCard = null;
    }

    public PlantCard getActivPlantCard() {
        return activPlantCard;
    }

    public void setSunManager(SunManager sunManager) {
        this.sunManager = sunManager;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public void setPlantCards(PlantCard[] plantCards) {
        this.plantCards = plantCards;
    }

    public String getName() {
        return name;
    }

    public SunManager getSunManager() {
        return sunManager;
    }

    public PlantCard[] getPlantCardsArray(){
        return this.plantCards;
    }

    public boolean canShovel() {
        return isShovelActive && activPlantCard == null;
    }

    public void toggleShovel() {
        this.cancelBuyPlant();
        this.isShovelActive = !isShovelActive;
    }

    public void shovelPlant(int x, int y) {
        partie.getOneRow(y).getPlantCase(x).removePlant();
    }

    public int getPlantPlacedCount() {
        return plantPlacedCount;
    }
}
