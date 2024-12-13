package fr.tanchou.pvz.game;

import fr.tanchou.pvz.abstractEnity.abstractPlant.Plant;
import fr.tanchou.pvz.entityRealisation.ObjectOfPlant.Sun;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.DoublePeaShooter;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.FreezePeaShooter;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.PeaShooter;
import fr.tanchou.pvz.entityRealisation.plants.ObjectGeneratorPlant.SunFlower;
import fr.tanchou.pvz.entityRealisation.plants.PotatoMine;
import fr.tanchou.pvz.entityRealisation.plants.passive.WallNut;
import fr.tanchou.pvz.game.rowComponent.Row;

import java.util.ArrayList;
import java.util.List;

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

    private int sunFlowersPlacedScore = -10;

    private final List<Integer> sunHistory = new ArrayList<>();  // Historique des valeurs de sold tous les 10 ticks

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

    public double calculateScore() {
        int mowers = 1;
        for (Row row : partie.getRows()) {
            if (row.getMower() != null) {
                mowers *= 2;
            }
        }

        // Scores individuels avec ajustements
        int mowersScore = mowers * 35; // Conservation des tondeuses.
        int survivalScore = partie.getZombieSpawner().getTotalTick() / 3; // Temps de survie avec un poids réduit.
        int zombieKillScore = getKilledZombieCount() * 25; // Récompense augmentée pour chaque zombie tué.
        int plantPlacementScore = getPlantPlacedCount() * 50; // Récompense augmentée pour les plantes posées.

        // Calcul de la moyenne de soleil collecté et application d'une pénalité si > 50
        double averageSun = calculateAverageSun();
        int unusedSunPenalty = averageSun > 50 ? - (int)(sold / 2) : 0;

        // Score total
        //System.out.println("Survival: " + survivalScore + ", Mowers: " + mowersScore + ", Plant Placement:" + getPlantPlacedCount() +  " " + plantPlacementScore + ", Zombie Kills: " + zombieKillScore + ", Unused Sun Penalty: " + unusedSunPenalty + ", SunFlower Placement: " + sunFlowersPlacedScore*10 + ", Victory: " + (partie.isVictory() ? 2000 : 0));
        return survivalScore + mowersScore + plantPlacementScore + zombieKillScore + unusedSunPenalty*0.8 + this.sunFlowersPlacedScore*10 + (partie.isVictory() ? 2000 : 0);
    }

    public void tick() {
        for (PlantCard plantCard : plantCards) {
            plantCard.tick(this.sold);
        }
        this.collectSun();

        // Enregistrer la valeur de sold dans l'historique tous les 10 ticks
        if (lastCollectSun % 10 == 0) {
            sunHistory.add(sold);
        }
    }

    private double calculateAverageSun() {
        if (sunHistory.isEmpty()) {
            return 0; // Si aucun tick n'a eu lieu, retourner 0
        }

        double sum = 0;
        for (int value : sunHistory) {
            sum += value;
        }
        return sum / sunHistory.size(); // Moyenne des derniers SUN_HISTORY_SIZE ticks
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

                if (activPlantCard.getPlant() instanceof SunFlower) {
                    if (x < 5){
                        sunFlowersPlacedScore += 5;
                    } else if (x >= 7) {
                        sunFlowersPlacedScore -= 2;
                    } else {
                        sunFlowersPlacedScore += 1;
                    }
                }

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

    public int getKilledZombieCount() {
        int killedZombieCount = 0;
        for (Row row : partie.getRows()) {
            killedZombieCount += row.getKilledZombieCount();
        }
        return killedZombieCount;
    }


}
