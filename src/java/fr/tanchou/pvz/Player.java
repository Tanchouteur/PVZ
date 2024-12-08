package fr.tanchou.pvz;

import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.DoublePeaShooter;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.FreezePeaShooter;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.PeaShooter;
import fr.tanchou.pvz.entityRealisation.plants.PlantCard;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.SunFlower;
import fr.tanchou.pvz.entityRealisation.plants.PotatoMine;
import fr.tanchou.pvz.entityRealisation.plants.passive.WallNut;
import fr.tanchou.pvz.game.Partie;
import fr.tanchou.pvz.game.SunManager;

public class Player {
    private int sold;
    private PlantCard[] plantCards;
    private final String name;
    private PlantCard activPlantCard;

    private  SunManager sunManager;
    private  Partie partie;

    private int lastCollectSun = 0;

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
            System.err.println("sold : " + sold);
        }
    }

    public int getSold() {
        return sold;
    }

    public boolean preBuyPlant(PlantCard plantCard) {
        if (this.activPlantCard != null) {

            cancelBuyPlant();

        }else if (plantCard.canBuy() && (this.sold - plantCard.getPlant().getCost()) >= 0) {
            //this.sold -= plantCard.getPlant().getCost();
            this.activPlantCard = plantCard;
            return true;
        }
        return false;
    }

    public boolean buyPlant(int x, int y) {
        if (activPlantCard != null && partie.getOneRow(y).getPlantCase(x).isEmpty()) {
            //this.sold += activPlantCard.getPlant().getCost();
            if (activPlantCard.canBuy()) {

                Plant plant = activPlantCard.getNewPlant(x, y);
                partie.getOneRow(y).placePlantInCase(plant);
                activPlantCard = null;
                this.sold -= plant.getCost();

                return true;
            }

        }
        return false;
    }

    public void cancelBuyPlant() {
        //this.sold += activPlantCard.getPlant().getCost();
        this.activPlantCard = null;
    }

    public PlantCard getActivPlantCard() {
        return activPlantCard;
    }

    private void setActivPlantCard(PlantCard activPlantCard) {
        this.activPlantCard = activPlantCard;
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

    public void setPlantCard(int index, PlantCard plantCard) {
        this.plantCards[index] = plantCard;
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
}
